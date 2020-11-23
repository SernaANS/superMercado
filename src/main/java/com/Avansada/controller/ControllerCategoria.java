package com.Avansada.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.Avansada.Modelo.Producto;

import com.Avansada.repository.RepoCategoria;
import com.Avansada.repository.RepoProducto;
import com.Avansada.service.api.ProductoServiceAPI;


@Controller
public class ControllerCategoria {

	
	@Autowired
	private final RepoCategoria repoCategoria;
	
	
	
	@Autowired
	private final RepoProducto repoProducto;

	@Autowired
	private ProductoServiceAPI productoServiceAPI;




	public ControllerCategoria(RepoCategoria repoCategoria, RepoProducto repoProducto,
			ProductoServiceAPI productoServiceAPI) {
		super();
		this.repoCategoria = repoCategoria;
		this.repoProducto = repoProducto;
		this.productoServiceAPI = productoServiceAPI;
	}

	@GetMapping("/GestionCategoria")
	public String showSignUpForm(Categoria categoria,Model model) {
		model.addAttribute("categoria", categoria);
		Iterable<Categoria> lista = repoCategoria.findAll();
		model.addAttribute("cantegorias", lista);
		return "GestionCategoria";
		
	}
	
	@GetMapping("/GestionCategoriaVendedor")
	public String GestionCategoriaVendedor(Categoria categoria,Model model) {
		model.addAttribute("categoria", categoria);
		Iterable<Categoria> lista = repoCategoria.findAll();
		model.addAttribute("cantegorias", lista);
		return "GestionCategoriaVendedor";
		
	}
	
	
	
	@GetMapping(value ="/Index")
	public String xxx(@RequestParam Map <String, Object> params,Model model) {
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
		
		PageRequest pageRequest = PageRequest.of(page,3);
		
		Page<Producto> pageProducto = productoServiceAPI.getAll(pageRequest);
		
		int totalPage = pageProducto.getTotalPages();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		model.addAttribute("productos", pageProducto.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
		
		return "Index";	
	}
	
@GetMapping("/*")
	public String showSignUpForm1(Model model) {
		Iterable<Producto> productos = repoProducto.findAll();
		model.addAttribute("productos", productos);
		return "redirect:/Index";
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
//-----------------vendedor-----------
	
	@PostMapping("/RegistrarCategoriaV")
	public String RegistarCategoriaV(@Validated Categoria categoria, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "GestionCategoriaVendedor";
		}
		if(!categoria.getNombre().isEmpty()||!categoria.getDescripcion().isEmpty()) {
			if(BuscarCategoriaNombre(categoria, result, model)!=null) {
				repoCategoria.save(categoria);
				Iterable<Categoria> lista = repoCategoria.findAll();
				model.addAttribute("cantegorias", lista);
				return "redirect:/GestionCategoriaVendedor";
			}else {
				return "redirect:/GestionCategoriaVendedor";
			}
		}else {
			return "redirect:/GestionCategoriaVendedor";
		}
	}

	@PostMapping("/ModificarCategoriaV")
	public String ModificarCategoriaV(@Validated Categoria categoria, BindingResult result,Model model) {
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
					return "redirect:/GestionCategoriaVendedor";
				}else {
					return "redirect:/GestionCategoriaVendedor";
				}
			}else {
				model.addAttribute("errorVacio", "Ingrese ");
				Iterable<Categoria> lista = repoCategoria.findAll();
				model.addAttribute("cantegorias", lista);
				return "redirect:/GestionCategoriaVendedor";
			}
			
		}else {
			return "redirect:/GestionCategoriaVendedor";
		}
		

	}

	//Este Funciona con ID
	public String BuscarCategoriaIDV(Categoria categoria, Model model) {
		Categoria buscaCategoria = repoCategoria.buscarCategoriaId(categoria.getIdCategoria());
		if (buscaCategoria != null) {
			model.addAttribute("categoria", repoCategoria.findAll());
			return "redirect:/GestionCategoriaVendedor";
			
		} else {
			return "GestionCategoriaVendedor";
		}

	}
	
	//Este con el Nombre
	@PostMapping("/BuscarCategoriaV")	
	public String BuscarCategoriaNombreV(Categoria categoria, BindingResult result,Model model) {
		Categoria buscaCategoria = repoCategoria.buscarCategoriaNombre(categoria.getNombre());
		
		if (buscaCategoria != null) {
			System.out.println(buscaCategoria.getNombre());
			model.addAttribute("categoria", buscaCategoria);
			return "GestionCategoriaVendedor";
			
		} else {
			return "GestionCategoriaVendedor";
		}

	}
	@GetMapping("/deleteCategoriaV")
	public String EliminarCategoriaV(@PathVariable("nombre") String nombre,BindingResult result, Model model) {
		Categoria categoria = repoCategoria.buscarCategoriaNombre(nombre);
		repoCategoria.delete(categoria);
		model.addAttribute("categoria", repoCategoria.findAll());
		return "redirect:/GestionCategoriaVendedor";
	}

	@RequestMapping(value = "/CategoriaV", method = RequestMethod.POST)
	public String handlePostV(@RequestParam(required = false, value = "Registrar") String registrar,
			@RequestParam(required = false, value = "Modificar") String modificar,
			@RequestParam(required = false, value = "Buscar") String Buscar, 
			@RequestParam(required = false, value = "Eliminar") String Eliminar, 
			@Validated Categoria categoria,BindingResult result, Model model) {

		if ("Registrar".equals(registrar)) {
			return RegistarCategoriaV(categoria, result, model);
		} else if ("Modificar".equals(modificar)) {
			return ModificarCategoriaV(categoria, result, model);
		}else if ("Eliminar".equals(Eliminar)) {
			return EliminarCategoriaV(categoria.getNombre(), result, model);
		}else if ("Buscar".equals(Buscar)) {
			return BuscarCategoriaNombreV(categoria,result, model);
		}
		return "GestionProvedor";
	}
}


