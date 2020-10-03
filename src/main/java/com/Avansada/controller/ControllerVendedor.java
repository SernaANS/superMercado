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

import com.Avansada.Modelo.Vendedor;
import com.Avansada.repository.RepoVendedor;

@Controller
public class ControllerVendedor {
	
	public static int cedula;
	
	public static int getCedula() {
		return cedula;
	}

	public static void setCedula(int cedula) {
		ControllerVendedor.cedula = cedula;
	}

	@Autowired
	private final RepoVendedor repoVendedor;

	@Autowired
	public ControllerVendedor(RepoVendedor repoVendedor) {
		super();
		this.repoVendedor = repoVendedor;
	}

	//////////////////////////// Vistas////////////////////////////////////////////
	@GetMapping("/LoginVendedor")
	public String Login(Vendedor vendedor, Model model) {
		return "LoginVendedor";
	}

	@GetMapping("/FormularioVendedor")
	public String Formulario(Vendedor vendedor, Model model) {
		model.addAttribute("vendedor", vendedor);
		return "RegistrarVendedor";
	}

	@GetMapping("/MiVendedor")
	public String Miperfil(Vendedor vendedor, Model model) {
		Vendedor Bvendedor = repoVendedor.BuscarVendedor(cedula);
		if (Bvendedor != null) {
			model.addAttribute("cliente", Bvendedor);
			return "MiPerfil";

		} else {
			return "index";
		}
	}

	@GetMapping("/IndexVendedor")
	public String IndexClienteLogeado(Vendedor vendedor, Model model) {
		return "indexVendedorLogiado";
	}

	/////////////////////////// Metodos////////////////////////////////////////////
	public String registar(Vendedor vendedor, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "GestionVendedor";
		}
		if (vendedor.getIdVendedor() >= 0 || !vendedor.getNombre().isEmpty() || vendedor.getDireccion() != ""
				|| vendedor.getApellido() != "" || vendedor.getTelefono() != "" 
				 || vendedor.getFechaNacimiento() != null) {
			if (BuscarCedula(vendedor, result, model) != null) {
				repoVendedor.save(vendedor);
				return "redirect:/Login";
			} else {
				return "redirect:/GestionCategoria";
			}
		} else {
			return "redirect:/GestionCategoria";
		}

	}

	public String BuscarCedula(Vendedor vendedor, BindingResult result, Model model) {

		Vendedor Bvendedor = repoVendedor.BuscarVendedor(cedula);
		if (Bvendedor != null) {
			model.addAttribute("cliente", Bvendedor);
			return "MiPerfil";

		} else {
			return "indexClienteLogiado";
		}

	}

	@PostMapping("/IniciarV")
	public String BuscarLogin(Vendedor vendedor, BindingResult result, Model model) {

		Vendedor Bvendedor = repoVendedor.loginVendedor(vendedor.getIdVendedor());
		if (Bvendedor != null) {
			model.addAttribute("Cliente", Bvendedor);
			cedula=vendedor.getIdVendedor();
			return "indexV";
		} else {
			return "/Index";
		}

	}

	public String Eliminar(Vendedor vendedor, BindingResult result, Model model) {
		Vendedor bCliente = repoVendedor.findById(vendedor.getIdVendedor())
				.orElseThrow(() -> new IllegalArgumentException("Invalid usuario Id:" + vendedor.getIdVendedor()));
		repoVendedor.delete(vendedor);
		return "redirect:/Index";
	}

	public String Modificar(Vendedor vendedor, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "index";
		}
		
		if (vendedor.getIdVendedor()!=0) {
			Vendedor newVendedor=repoVendedor.BuscarVendedor(vendedor.getIdVendedor());
			if (newVendedor!=null) {
				newVendedor.setNombre(vendedor.getNombre());
				newVendedor.setDireccion(vendedor.getDireccion());
				newVendedor.setTelefono(vendedor.getTelefono());
				newVendedor.setApellido(vendedor.getApellido());
				newVendedor.setFechaNacimiento(vendedor.getFechaNacimiento());
				repoVendedor.save(newVendedor);
				model.addAttribute("Mensaje", "logrado");
				return "indexClienteLogiado";
			}else {
				return "redirect:/Iniciar";
			}
		}else {
			return "redirect:/GestionProvedor";
		}
	}

	public void listar(Vendedor vendedor, BindingResult result, Model model) {
		Iterable<Vendedor> lista = repoVendedor.findAll();
		model.addAttribute("lista", lista);
	}
	////////////////////////// Bottones///////////////////////////////////////////

	public void BottonLogin() {
	}

	@RequestMapping(value = "/perfilV", method = RequestMethod.POST)
	public String BottonMiperfil(@RequestParam(required = false, value = "Modificar") String modificar,
			@RequestParam(required = false, value = "Eliminar") String eliminar,
			@Validated Vendedor vendedor, BindingResult result, Model model) {
		
		if ("Modificar".equals(modificar)) {
			return Modificar(vendedor, result, model);
		}else if ("Eliminar".equals(eliminar)) {
			return Eliminar(vendedor, result, model);
		}
		
		return "index";
	}

	@RequestMapping(value = "/RegistroV", method = RequestMethod.POST)
	public String BottonFormulario(@RequestParam(required = false, value = "RegistrarV") String registrar,
			@Validated Vendedor vendedor, BindingResult result, Model model) {

		if ("RegistrarV".equals(registrar)) {
			return registar(vendedor, result, model);
		}

		return "/Index";

	}



}
