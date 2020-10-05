package com.Avansada.controller;


import java.util.Date;


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

import com.Avansada.Modelo.Bodega;

import com.Avansada.Modelo.DetalleBodega;
import com.Avansada.Modelo.Producto;

import com.Avansada.repository.RepoBodega;

import com.Avansada.repository.RepoDetalleBodega;
import com.Avansada.repository.RepoProducto;


@Controller
public class ControllerDetalleBodega {
	
	@Autowired
	private final RepoProducto repoProducto;
	@Autowired
	private final RepoBodega repoBodega;
	@Autowired
	private final RepoDetalleBodega repoDetalleBodega;

    @Autowired
    public ControllerDetalleBodega(RepoBodega repoBodega,RepoProducto repoProducto, RepoDetalleBodega repoDetalleBodega) {
		this.repoBodega=repoBodega;
		this.repoProducto=repoProducto;
		this.repoDetalleBodega=repoDetalleBodega;
	}

	@GetMapping("/GestionDetalleBodega")
	public String showSignUpForm(DetalleBodega detalleBodega,Model model) {
		
		//Carga la lista de productos
		Iterable<Producto > listaProductos=repoProducto.findAll();
		model.addAttribute("productos",listaProductos);

		Iterable<Bodega> listaBodegas=repoBodega.findAll();
		model.addAttribute("bodeguitas",listaBodegas);
		
		Iterable<DetalleBodega> listaDetallesBodegas=repoDetalleBodega.findAll();
		model.addAttribute("detalleBodeguita",listaDetallesBodegas);
		
		Producto pro=null;
		model.addAttribute("datosProducto",pro);
		
		
		return "GestionDetalleBodega";
	}
	
	@GetMapping("/GestionDetalleProducto/detalleBodega/{idDetalleBodega}")
	public String showSignUpForm1(@PathVariable("idDetalleBodega") Integer id,DetalleBodega detalleBodega,Model model) {
		DetalleBodega bodeguita= repoDetalleBodega.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalidanuncio Id:" + id));
		if(bodeguita!=null) {
			System.out.println("bodeguita"+ bodeguita.getIdDetalleBodega()+"---"+bodeguita.getCantidadMinima());
			
			
			model.addAttribute("detalles",bodeguita);
			
			Iterable<Bodega> listaBodegas=repoBodega.findAll();
			model.addAttribute("bodeguitas",listaBodegas);
			return "ModificarDetalleBodega";
		}else{
			return "redirect:/GestionDetalleBodega";
		}
		
		
	}
	
	
	@GetMapping("/GestionDetalleBodega/producto/{idProducto}")
	public String showUpdateForm(@PathVariable("idProducto") Integer id,DetalleBodega detalleBodega, Model model) {
		Producto producto = repoProducto.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalidanuncio Id:" + id));
		
		Iterable<Producto > listaProductos=repoProducto.findAll();
		model.addAttribute("productos",listaProductos);

		Iterable<Bodega> listaBodegas=repoBodega.findAll();
		model.addAttribute("bodeguitas",listaBodegas);
		detalleBodega.setProducto(producto);
		
		Iterable<DetalleBodega> listaDetallesBodegas=repoDetalleBodega.findAll();
		model.addAttribute("detalleBodeguita",listaDetallesBodegas);
		
		model.addAttribute("datosProducto", producto);
		return "GestionDetalleBodega";
	}
	

	




	@PostMapping("/RegistrarDetalleBodega")
	public String RegistarDetalleBodega(@Validated DetalleBodega detalleBodega, BindingResult result, Model model) {
		if (result.hasErrors()) {
			
			return "GestionDetalleBodega";
		}
		Date ahora = new Date();
		detalleBodega.setFecha(ahora);
				repoDetalleBodega.save(detalleBodega);
				return "redirect:/GestionDetalleBodega";
	
	}
	
	

		
	@PostMapping("/GestionDetalleProducto/DetalleBodegaUpdate/{idDetalleBodega}")
	public String ModificarDetalleBodega(@PathVariable("idDetalleBodega") Integer id,@Validated DetalleBodega detalleBodega, BindingResult result,Model model) {
		if (result.hasErrors()) {
			return "listarMensajeVendedor";
		}
		
		if (detalleBodega!=null) {
			DetalleBodega buscadetalleBodega = repoDetalleBodega.buscarDetalleBodegaId(detalleBodega.getIdDetalleBodega());
			if (buscadetalleBodega!=null) {		
				repoDetalleBodega.save(detalleBodega);
				model.addAttribute("Mensaje", "logrado");
				return "redirect:/GestionDetalleBodega";
			}else {
				return "redirect:/GestionDetalleBodega";
			}
		}else {
			return "redirect:/GestionDetalleBodega";
		}
	}
	



	@PostMapping("/BuscarDetalleBodega")
	public String BuscarDetalleBodega(DetalleBodega detalleBodega, BindingResult result, Model model) {
		DetalleBodega buscaDetalleBodega = repoDetalleBodega.buscarDetalleBodegaId(detalleBodega.getIdDetalleBodega());
		
		if (buscaDetalleBodega != null) {
			//Devuleve el objeto buscado
			 return "GestionDetalleBodega";
			
		} else {
			return "GestionDetalleBodega";
		}

	}

	@GetMapping("/deleteDetalleBodega")
	public String EliminarDetalleBodega(@PathVariable("id") int id,BindingResult result, Model model) {
		DetalleBodega detalleBodega = repoDetalleBodega.buscarDetalleBodegaId(id);
		repoDetalleBodega.delete(detalleBodega);
		return "redirect:/GestionDetalleBodega";
	}

	@RequestMapping(value = "/DetalleBodega", method = RequestMethod.POST)
	public String handlePost(
			@RequestParam(required = false, value = "Registrar") String registrar,
			@Validated DetalleBodega detalleBodega,BindingResult result, Model model) {

		if ("Registrar".equals(registrar)) {
			return RegistarDetalleBodega(detalleBodega, result, model);
		}
		return "GestionDetalleBodega";
	}

	@RequestMapping(value = "/DetalleBodegaUpdate", method = RequestMethod.POST)
	public String handlePostq(
			@RequestParam(required = false, value = "Modificar") String modificar,
			@RequestParam(required = false, value = "Buscar") String Buscar, 
			@RequestParam(required = false, value = "Eliminar") String Eliminar, 
			@Validated DetalleBodega detalleBodega,BindingResult result, Model model) {

		if ("Modificar".equals(modificar)) {
			return ModificarDetalleBodega(detalleBodega.getIdDetalleBodega(),detalleBodega, result, model);
		}else if ("Eliminar".equals(Eliminar)) {
			return EliminarDetalleBodega(detalleBodega.getIdDetalleBodega(), result, model);
		}else if ("Buscar".equals(Buscar)) {
			return BuscarDetalleBodega(detalleBodega,result, model);
		}
		return "Index";
	}
}