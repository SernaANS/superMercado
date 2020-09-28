package com.Avansada.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Avansada.Modelo.Categoria;
import com.Avansada.Modelo.Cliente;
import com.Avansada.Modelo.Proveedor;
import com.Avansada.repository.RepoCliente;

@Controller
public class ControllerCliente {

	
	public static int cedula;
	
	public static int getCedula() {
		return cedula;
	}

	public static void setCedula(int cedula) {
		ControllerCliente.cedula = cedula;
	}

	@Autowired
	private final RepoCliente repoCliente;

	@Autowired
	public ControllerCliente(RepoCliente repoCliente) {
		super();
		this.repoCliente = repoCliente;
	}

	//////////////////////////// Vistas////////////////////////////////////////////
	@GetMapping("/Login")
	public String Login(Cliente cliente, Model model) {
		return "LoginCliente";
	}

	@GetMapping("/Formulario")
	public String Formulario(Cliente cliente, Model model) {
		model.addAttribute("cliente", cliente);
		return "RegistrarCliente";
	}

	@GetMapping("/Miperfil")
	public String Miperfil(Cliente cliente, Model model) {
		Cliente Bcliente = repoCliente.BuscarCLiente(cedula);
		if (Bcliente != null) {
			model.addAttribute("cliente", Bcliente);
			return "MiPerfil";

		} else {
			return "index";
		}
	}

	@GetMapping("/IndexCliente")
	public String IndexClienteLogeado(Cliente cliente, Model model) {
		return "indexClienteLogiado";
	}

	/////////////////////////// Metodos////////////////////////////////////////////
	public String registar(Cliente cliente, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "GestionCategoria";
		}
		if (cliente.getIdCliente() != 0 || !cliente.getNombre().isEmpty() || cliente.getDireccion() != ""
				|| cliente.getApellido() != "" || cliente.getTelefono() != "" || cliente.getCorreo() != ""
				|| cliente.getClave() != "" || cliente.getFechaNacimiento() != null) {
			if (BuscarCedula(cliente, result, model) != null) {
				repoCliente.save(cliente);
				return "redirect:/Login";
			} else {
				return "redirect:/GestionCategoria";
			}
		} else {
			return "redirect:/GestionCategoria";
		}

	}

	public String BuscarCedula(Cliente cliente, BindingResult result, Model model) {

		Cliente Bcliente = repoCliente.BuscarCLiente(cedula);
		if (Bcliente != null) {
			model.addAttribute("cliente", Bcliente);
			return "MiPerfil";

		} else {
			return "indexClienteLogiado";
		}

	}

	@PostMapping("/Iniciar")
	public String BuscarLogin(Cliente cliente, BindingResult result, Model model) {

		Cliente Bcliente = repoCliente.login(cliente.getIdCliente(), cliente.getClave());
		if (Bcliente != null) {
			model.addAttribute("Cliente", Bcliente);
			cedula=cliente.getIdCliente();
			return "indexClienteLogiado";
		} else {
			return "/Index";
		}

	}

	public String Eliminar(Cliente cliente, BindingResult result, Model model) {
		Cliente bCliente = repoCliente.findById(cliente.getIdCliente())
				.orElseThrow(() -> new IllegalArgumentException("Invalid usuario Id:" + cliente.getIdCliente()));
		repoCliente.delete(cliente);
		return "redirect:/Index";
	}

	public String Modificar(Cliente cliente, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "index";
		}
		
		if (cliente.getIdCliente()!=0) {
			Cliente newCleinte=repoCliente.BuscarCLiente(cliente.getIdCliente());
			if (newCleinte!=null) {
				newCleinte.setNombre(cliente.getNombre());
				newCleinte.setDireccion(cliente.getDireccion());
				newCleinte.setTelefono(cliente.getTelefono());
				newCleinte.setApellido(cliente.getApellido());
				newCleinte.setCorreo(cliente.getCorreo());
				newCleinte.setFechaNacimiento(cliente.getFechaNacimiento());
				repoCliente.save(newCleinte);
				model.addAttribute("Mensaje", "logrado");
				return "indexClienteLogiado";
			}else {
				return "redirect:/Iniciar";
			}
		}else {
			return "redirect:/GestionProvedor";
		}
	}

	public void listar(Cliente cliente, BindingResult result, Model model) {
		Iterable<Cliente> lista = repoCliente.findAll();
		model.addAttribute("lista", lista);
	}
	////////////////////////// Bottones///////////////////////////////////////////

	public void BottonLogin() {
	}

	@RequestMapping(value = "/perfil", method = RequestMethod.POST)
	public String BottonMiperfil(@RequestParam(required = false, value = "Modificar") String modificar,
			@RequestParam(required = false, value = "Eliminar") String eliminar,
			@Validated Cliente cliente, BindingResult result, Model model) {
		
		if ("Modificar".equals(modificar)) {
			return Modificar(cliente, result, model);
		}else if ("Eliminar".equals(eliminar)) {
			return Eliminar(cliente, result, model);
		}
		
		return "index";
	}

	@RequestMapping(value = "/Registro", method = RequestMethod.POST)
	public String BottonFormulario(@RequestParam(required = false, value = "Registrar") String registrar,
			@Validated Cliente cliente, BindingResult result, Model model) {

		if ("Registrar".equals(registrar)) {
			return registar(cliente, result, model);
		}

		return "/Index";

	}

}
