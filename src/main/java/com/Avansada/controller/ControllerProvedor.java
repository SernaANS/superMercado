package com.Avansada.controller;


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

import com.Avansada.Modelo.Proveedor;
import com.Avansada.Modelo.Subcategoria;
import com.Avansada.repository.RepoProvedor;


@Controller
public class ControllerProvedor {

	@Autowired
	private final RepoProvedor repoProvedor;

	@Autowired
	public ControllerProvedor(RepoProvedor repoProvedor) {
		super();
		this.repoProvedor = repoProvedor;
	}

	@GetMapping("/GestionProvedor")
	public String showSignUpForm(Proveedor provedor,Model model) {
		Iterable<Proveedor> lista = repoProvedor.findAll();
		model.addAttribute("lista", lista);
		model.addAttribute("provedor", provedor);
		return "GestionProvedor";
		
	}

	@PostMapping("/RegistrarProvedor")
	public String RegistarProvedor(@Validated Proveedor provedor, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "GestionProvedor";
		}
		if(provedor.getIdProveedor()!=0) {
			repoProvedor.save(provedor);
			Iterable<Proveedor> lista = repoProvedor.findAll();
			model.addAttribute("lista", lista);
			model.addAttribute("provedor", repoProvedor.findAll());
			return "redirect:/GestionProvedor";
		}
		
		return "GestionProvedor";
	}

	@PostMapping("/ModificarProvedor")
	public String ModificarProvedor(@Validated Proveedor provedor, BindingResult result,Model model) {
		
		if (result.hasErrors()) {
			return "redirect:/GestionProvedor";
		}
		
		if (provedor.getIdProveedor()!=0) {
			Proveedor nuevoProveedor=repoProvedor.buscarProveedor(provedor.getIdProveedor());
			if (nuevoProveedor!=null) {
				nuevoProveedor.setNombre(provedor.getNombre());
				nuevoProveedor.setDireccion(provedor.getDireccion());
				nuevoProveedor.setTelefono(provedor.getTelefono());
				repoProvedor.save(nuevoProveedor);
				Iterable<Proveedor> lista = repoProvedor.findAll();
				model.addAttribute("lista", lista);
				model.addAttribute("Mensaje", "logrado");
				return "redirect:/GestionProvedor";
			}else {
				return "redirect:/GestionProvedor";
			}
		}else {
			return "redirect:/GestionProvedor";
		}
		

	}

	@PostMapping("/Buscar")
	public String BuscarProvedor(Proveedor provedor, Model model) {
		Proveedor buscaProvedor = repoProvedor.buscarProveedor(provedor.getIdProveedor());
		if (buscaProvedor != null) {
			Iterable<Proveedor> lista = repoProvedor.findAll();
			model.addAttribute("lista", lista);
			model.addAttribute("provedor", buscaProvedor);
			return "GestionProvedor";
			
		} else {
			return "GestionProvedor";
		}

	}

	@GetMapping("/deleteUsuario")
	public String EliminarProvedro(@PathVariable("cedula") Integer cedula,BindingResult result, Model model) {
		Proveedor provedor = repoProvedor.findById(cedula)
				.orElseThrow(() -> new IllegalArgumentException("Invalid usuario Id:" + cedula));
		repoProvedor.delete(provedor);
		Iterable<Proveedor> lista = repoProvedor.findAll();
		model.addAttribute("lista", lista);
		model.addAttribute("provedor", repoProvedor.findAll());
		return "redirect:/GestionProvedor";
	}

	@RequestMapping(value = "/Provedor", method = RequestMethod.POST)
	public String handlePost(@RequestParam(required = false, value = "Registrar") String registrar,
			@RequestParam(required = false, value = "Modificar") String modificar,
			@RequestParam(required = false, value = "Buscar") String Buscar, 
			@RequestParam(required = false, value = "Eliminar") String Eliminar, 
			@Validated Proveedor provedor,BindingResult result, Model model) {

		if ("Registrar".equals(registrar)) {
			return RegistarProvedor(provedor, result, model);
		} else if ("Modificar".equals(modificar)) {
			return ModificarProvedor(provedor, result, model);
		}else if ("Eliminar".equals(Eliminar)) {
			return EliminarProvedro(provedor.getIdProveedor(), result, model);
		}else if ("Buscar".equals(Buscar)) {
			return BuscarProvedor(provedor, model);
		}
		return "GestionProvedor";
	}
	
	
	
	

}
