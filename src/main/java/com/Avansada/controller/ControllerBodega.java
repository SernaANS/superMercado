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

import com.Avansada.Modelo.Bodega;
import com.Avansada.Modelo.Categoria;
import com.Avansada.Modelo.Vendedor;
import com.Avansada.repository.RepoBodega;


@Controller
public class ControllerBodega {

	private final RepoBodega repoBodega;

	public ControllerBodega(RepoBodega repoBodega) {
		super();
		this.repoBodega = repoBodega;
	}

	@GetMapping("/IndexAdminLogiado")
	public String Login(Vendedor vendedor, Model model) {
		return "IndexAdminLogiado";
	}
	
	@GetMapping("/GestionBodega")
	public String showSignUpForm(Bodega bodega,Model model) {
		Iterable<Bodega> lista = repoBodega.findAll();
		model.addAttribute("bodegitas", lista);
		return "GestionBodega";
	}
	
	
	@PostMapping("/RegistrarBodega")
	public String RegistarBodega(@Validated Bodega bodega, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "GestionBodega";
		}
		
			if(BuscarBodegaNombre(bodega, result, model)!=null) {
				repoBodega.save(bodega);
				Iterable<Bodega> lista = repoBodega.findAll();
				model.addAttribute("bodegitas", lista);
				return "redirect:/GestionBodega";
			}else {
				return "redirect:/GestionBodega";
			}
	
	}

	@PostMapping("/ModificarBodega")
	public String ModificarBodega(@Validated Bodega bodega, BindingResult result,Model model) {
		if (result.hasErrors()) {
			return "listarMensajeVendedor";
		}
		
		if ( bodega!=null) {	
				Bodega buscaBodega = repoBodega.buscarBodegaNombre(bodega.getNombre());
				if (buscaBodega!=null) {
					buscaBodega.setNombre( bodega.getNombre());
					buscaBodega.setDireccion(bodega.getDireccion());
					buscaBodega.setEspacioMaximo(bodega.getEspacioMaximo());
					
					repoBodega.save(buscaBodega);
					model.addAttribute("Mensaje", "logrado");
					Iterable<Bodega> lista = repoBodega.findAll();
					model.addAttribute("bodegitas", lista);
					return "GestionBodega";
				}else {
					return "redirect:/GestionBodega";
				}
			}else {
				model.addAttribute("errorVacio", "Ingrese ");
				Iterable<Bodega> lista = repoBodega.findAll();
				model.addAttribute("bodegitas", lista);
				return "redirect:/GestionBodega";
			}
			

		

	}

	//Este Funciona con ID
	public String BuscarBodegaID(Bodega bodega, Model model) {
		Bodega buscaBodega = repoBodega.buscarBodegaId(bodega.getIdBodega());
		if (buscaBodega != null) {
			model.addAttribute("bodega", repoBodega.findAll());
			return "redirect:/GestionBodega";
			
		} else {
			return "GestionBodega";
		}

	}
	
	//Este con el Nombre
	@PostMapping("/BuscarBodega")	
	public String BuscarBodegaNombre(Bodega bodega, BindingResult result,Model model) {
		Bodega buscaBodega = repoBodega.buscarBodegaNombre(bodega.getNombre());
		
		if (buscaBodega != null) {
			System.out.println(buscaBodega.getNombre());
			model.addAttribute("bodegitas", buscaBodega);
			return "GestionBodega";
			
		} else {
			return "GestionBodega";
		}

	}
	@GetMapping("/deleteBodega")
	public String EliminarBodega(@PathVariable("nombre") String nombre,BindingResult result, Model model) {
		Bodega bodega = repoBodega.buscarBodegaNombre(nombre);
		repoBodega.delete(bodega);
		model.addAttribute("bodega", repoBodega.findAll());
		return "redirect:/GestionBodega";
	}

	@RequestMapping(value = "/Bodega", method = RequestMethod.POST)
	public String handlePost(
			@RequestParam(required = false, value = "Registrar") String registrar,
			@RequestParam(required = false, value = "Modificar") String modificar,
			@RequestParam(required = false, value = "Buscar") String Buscar, 
			@RequestParam(required = false, value = "Eliminar") String Eliminar, 
			@Validated Bodega bodega,BindingResult result, Model model) {

		if ("Registrar".equals(registrar)) {
			return RegistarBodega(bodega, result, model);
		} else if ("Modificar".equals(modificar)) {
			return ModificarBodega(bodega, result, model);
		}else if ("Eliminar".equals(Eliminar)) {
			return EliminarBodega(bodega.getNombre(), result, model);
		}else if ("Buscar".equals(Buscar)) {
			return BuscarBodegaNombre(bodega,result, model);
		}
		return "GestionBodega";
	}
}
