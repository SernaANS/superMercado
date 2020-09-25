package com.Avansada.controller;

import java.util.ArrayList;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Avansada.Modelo.Subcategoria;
import com.Avansada.Modelo.Categoria;
import com.Avansada.repository.RepoSubcategoria;
import com.Avansada.repository.RepoCategoria;

@Controller
public class ControllerSubcategoria {
	@Autowired
	private final RepoSubcategoria repoSubcategoria;
	@Autowired
	private final RepoCategoria repoCategoria;

    @Autowired
    public ControllerSubcategoria(RepoSubcategoria repoSubcategoria,RepoCategoria repoCategoria) {
		this.repoSubcategoria = repoSubcategoria;
		this.repoCategoria=repoCategoria;
	}

	@GetMapping("/GestionSubcategoria")
	public String showSignUpForm(Subcategoria subcategoria,Model model) {
		//Carga la lista de Subcategorias Existentes
		Iterable<Subcategoria> lista = repoSubcategoria.findAll();
		model.addAttribute("subcantegorias", lista);
		
		//Para cargar el combobox categorias
		Iterable<Categoria> listaCategorias = repoCategoria.todasCategoria();
		model.addAttribute("cantegorias", listaCategorias);
		
		return "GestionSubcategoria";
		
	}


	@PostMapping("/RegistrarSubcategoria")
	public String RegistarSubcategoria(@Validated Subcategoria subcategoria, BindingResult result, Model model) {
		if (result.hasErrors()) {
			
			//Carga la lista de Subcategorias Existentes
			Iterable<Subcategoria> lista = repoSubcategoria.findAll();
			model.addAttribute("subcantegorias", lista);
			
			//Para cargar el combobox categorias
			Iterable<Categoria> listaCategorias = repoCategoria.todasCategoria();
			model.addAttribute("cantegorias", listaCategorias);
			return "GestionSubcategoria";
		}
		//Valida si alguno de los campos esta vacio
		if(!subcategoria.getNombre().equals("")||!subcategoria.getDescripcion().equals("")) {
			Categoria cate=repoCategoria.buscarCategoriaNombre(subcategoria.getNombre());
			Subcategoria sub=repoSubcategoria.buscarSubcategoriaNombre(subcategoria.getNombre());
			if(cate==null&&sub==null) {
				repoSubcategoria.save(subcategoria);
				return "redirect:/GestionSubcategoria";
			}else {
				//Devolver un Mensaje De Error Que Ya existe(Hacer)
				return "redirect:/GestionSubcategoria";
			}
		}else {
			repoSubcategoria.save(subcategoria);
			return "redirect:/GestionSubcategoria";
		}
		

	}

	@PostMapping("/ModificarSubcategoria")
	public String ModificarSubcategoria(@Validated Subcategoria subcategoria, BindingResult result,Model model) {
		
		if (result.hasErrors()) {
			return "listarMensajeVendedor";
		}
		
		if (subcategoria!=null) {
			Subcategoria buscaSubcategoria = repoSubcategoria.buscarSubcategoriaNombre(subcategoria.getNombre());
			if (buscaSubcategoria!=null) {
				buscaSubcategoria.setDescripcion(subcategoria.getDescripcion());
				
				Categoria cat=subcategoria.getCategoria();
				cat.setIdCategoria(subcategoria.getCategoria().getIdCategoria());
				buscaSubcategoria.setCategoria(cat);
				
				repoSubcategoria.save(buscaSubcategoria);
				
				model.addAttribute("Mensaje", "logrado");
				return "redirect:/GestionSubcategoria";
			}else {
				return "redirect:/GestionSubcategoria";
			}
		}else {
			return "redirect:/GestionSubcategoria";
		}
		

	}

	@PostMapping("/BuscarSubcategoria")
	public String BuscarSubcategoria(Subcategoria subcategoria, BindingResult result, Model model) {
		Subcategoria buscaSubcategoria = repoSubcategoria.buscarSubcategoriaNombre(subcategoria.getNombre());
		
		if (buscaSubcategoria != null) {
			//Devuleve el objeto buscado
			model.addAttribute("subcategoria", buscaSubcategoria);
			
			//Carga la lista de Subcategorias Existentes
			Iterable<Subcategoria> lista = repoSubcategoria.findAll();
			model.addAttribute("subcantegorias", lista);
			
			//Para cargar el combobox categorias
			Iterable<Categoria> listaCategorias = repoCategoria.todasCategoria();
			model.addAttribute("cantegorias", listaCategorias);
			 return "GestionSubcategoria";
			
		} else {
			return "GestionSubcategoria";
		}

	}

	@GetMapping("/deleteSubcategoria")
	public String EliminarSubcategoria(@PathVariable("nombre") String nombre,BindingResult result, Model model) {
		Subcategoria subcategoria = repoSubcategoria.buscarSubcategoriaNombre(nombre);
		repoSubcategoria.delete(subcategoria);
		model.addAttribute("subcategoria", repoSubcategoria.findAll());
		return "redirect:/GestionSubcategoria";
	}

	@RequestMapping(value = "/Subcategoria", method = RequestMethod.POST)
	public String handlePost(@RequestParam(required = false, value = "Registrar") String registrar,
			@RequestParam(required = false, value = "Modificar") String modificar,
			@RequestParam(required = false, value = "Buscar") String Buscar, 
			@RequestParam(required = false, value = "Eliminar") String Eliminar, 
			@Validated Subcategoria subcategoria,BindingResult result, Model model) {

		if ("Registrar".equals(registrar)) {
			return RegistarSubcategoria(subcategoria, result, model);
		} else if ("Modificar".equals(modificar)) {
			return ModificarSubcategoria(subcategoria, result, model);
		}else if ("Eliminar".equals(Eliminar)) {
			return EliminarSubcategoria(subcategoria.getNombre(), result, model);
		}else if ("Buscar".equals(Buscar)) {
			return BuscarSubcategoria(subcategoria,result, model);
		}
		return "GestionSubcategoria";
	}

}


