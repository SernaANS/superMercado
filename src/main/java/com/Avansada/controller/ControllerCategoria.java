package com.Avansada.controller;

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

import com.Avansada.Modelo.Categoria;
import com.Avansada.repository.RepoCategoria;


@Controller
public class ControllerCategoria {

	private final RepoCategoria repoCategoria;

	public ControllerCategoria(RepoCategoria repoCategoria) {
		super();
		this.repoCategoria = repoCategoria;
	}

	@GetMapping("/GestionCategoria")
	public String showSignUpForm(Categoria categoria,Model model) {
		model.addAttribute("categoria", categoria);
		Iterable<Categoria> lista = repoCategoria.findAll();
		model.addAttribute("cantegorias", lista);
		return "GestionCategoria";
		
	}
	
	@GetMapping("/Index")
	public String showSignUpForm(Model model) {
		return "Index";
		
	}
	
	@PostMapping("/RegistrarCategoria")
	public String RegistarCategoria(@Validated Categoria categoria, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "GestionCategoria";
		}
		if(!categoria.getNombre().isEmpty()||!categoria.getDescripcion().isEmpty()) {
			if(BuscarCategoriaNombre(categoria, result, model)!=null) {
				repoCategoria.save(categoria);
				Iterable<Categoria> lista = repoCategoria.findAll();
				model.addAttribute("cantegorias", lista);
				return "redirect:/GestionCategoria";
			}else {
				return "redirect:/GestionCategoria";
			}
		}else {
			return "redirect:/GestionCategoria";
		}
	}

	@PostMapping("/ModificarCategoria")
	public String ModificarCategoria(@Validated Categoria categoria, BindingResult result,Model model) {
		if (result.hasErrors()) {
			return "listarMensajeVendedor";
		}
		
		if (categoria!=null) {
			if(!categoria.getDescripcion().isEmpty()||!categoria.getNombre().isEmpty()) {
				Categoria buscaCategoria = repoCategoria.buscarCategoriaNombre(categoria.getNombre());
				if (buscaCategoria!=null) {
					buscaCategoria.setNombre(categoria.getNombre());
					buscaCategoria.setDescripcion(categoria.getDescripcion());
					
					repoCategoria.save(buscaCategoria);
					model.addAttribute("Mensaje", "logrado");
					Iterable<Categoria> lista = repoCategoria.findAll();
					model.addAttribute("cantegorias", lista);
					return "redirect:/GestionCategoria";
				}else {
					return "redirect:/GestionCategoria";
				}
			}else {
				model.addAttribute("errorVacio", "Ingrese ");
				Iterable<Categoria> lista = repoCategoria.findAll();
				model.addAttribute("cantegorias", lista);
				return "redirect:/GestionCategoria";
			}
			
		}else {
			return "redirect:/GestionCategoria";
		}
		

	}

	//Este Funciona con ID
	public String BuscarCategoriaID(Categoria categoria, Model model) {
		Categoria buscaCategoria = repoCategoria.buscarCategoriaId(categoria.getIdCategoria());
		if (buscaCategoria != null) {
			model.addAttribute("categoria", repoCategoria.findAll());
			return "redirect:/GestionCategoria";
			
		} else {
			return "GestionCategoria";
		}

	}
	
	//Este con el Nombre
	@PostMapping("/BuscarCategoria")	
	public String BuscarCategoriaNombre(Categoria categoria, BindingResult result,Model model) {
		Categoria buscaCategoria = repoCategoria.buscarCategoriaNombre(categoria.getNombre());
		
		if (buscaCategoria != null) {
			System.out.println(buscaCategoria.getNombre());
			model.addAttribute("categoria", buscaCategoria);
			return "GestionCategoria";
			
		} else {
			return "GestionCategoria";
		}

	}
	@GetMapping("/deleteCategoria")
	public String EliminarCategoria(@PathVariable("nombre") String nombre,BindingResult result, Model model) {
		Categoria categoria = repoCategoria.buscarCategoriaNombre(nombre);
		repoCategoria.delete(categoria);
		model.addAttribute("categoria", repoCategoria.findAll());
		return "redirect:/GestionCategoria";
	}

	@RequestMapping(value = "/Categoria", method = RequestMethod.POST)
	public String handlePost(@RequestParam(required = false, value = "Registrar") String registrar,
			@RequestParam(required = false, value = "Modificar") String modificar,
			@RequestParam(required = false, value = "Buscar") String Buscar, 
			@RequestParam(required = false, value = "Eliminar") String Eliminar, 
			@Validated Categoria categoria,BindingResult result, Model model) {

		if ("Registrar".equals(registrar)) {
			return RegistarCategoria(categoria, result, model);
		} else if ("Modificar".equals(modificar)) {
			return ModificarCategoria(categoria, result, model);
		}else if ("Eliminar".equals(Eliminar)) {
			return EliminarCategoria(categoria.getNombre(), result, model);
		}else if ("Buscar".equals(Buscar)) {
			return BuscarCategoriaNombre(categoria,result, model);
		}
		return "GestionProvedor";
	}

}


