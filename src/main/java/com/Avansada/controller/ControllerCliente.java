package com.Avansada.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import com.Avansada.Modelo.Cliente;
import com.Avansada.Modelo.DetalleFactura;
import com.Avansada.Modelo.Producto;
import com.Avansada.repository.RepoCliente;
import com.Avansada.repository.RepoDetallaFactura;
import com.Avansada.repository.RepoProducto;
import com.Avansada.service.api.ProductoServiceAPI;


@Controller
public class ControllerCliente {

	public static ArrayList<Producto> misProductos= new ArrayList<Producto>();
	

	
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
	private final RepoProducto repoProducto;
	RepoDetallaFactura repoDetallaFactura;
	
	@Autowired
	private ProductoServiceAPI productoServiceAPI;

	@Autowired
	public ControllerCliente(RepoCliente repoCliente,RepoProducto repoProducto,RepoDetallaFactura repoDetallaFactura,ProductoServiceAPI productoServiceAPI) {
		super();
		this.repoCliente = repoCliente;
		this.repoProducto = repoProducto;
		this.repoDetallaFactura=repoDetallaFactura;
		this.productoServiceAPI = productoServiceAPI;
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
			String nombre=Bcliente.getNombre();
			model.addAttribute("cliente", Bcliente);
			model.addAttribute("nombre", nombre);
			return "MiPerfil";

		} else {
			return "index";
		}
	}

	
	public String IndexClienteLogeado( Model model) {
		Cliente Bcliente = repoCliente.BuscarCLiente(cedula);
		
		if (Bcliente != null) {
			String nombre=Bcliente.getNombre();
			Iterable<Producto> productos = repoProducto.findAll();
			model.addAttribute("productos", productos);
			model.addAttribute("detalleFactura", new DetalleFactura());
			model.addAttribute("nombre", nombre);
			model.addAttribute("pro", "nada");
			return "indexClienteLogiado";
		}else {
			return "index";
		}
		
	}
	@GetMapping("/IndexCliente")
	public String IndexClienteLogeado(@RequestParam Map <String, Object> params,Model model) {

		Cliente Bcliente = repoCliente.BuscarCLiente(cedula);
		
		if (Bcliente != null) {
			String nombre=Bcliente.getNombre();
			model.addAttribute("detalleFactura", new DetalleFactura());
			model.addAttribute("nombre", nombre);
			model.addAttribute("pro", "nada");
			
			//PAGINACION
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
			return "indexClienteLogiado";
		}else {
			return "index";
		}
	}
	
	
	
	@GetMapping("/Micarrito")
	public String Micarrito(Cliente cliente, Model model) {
		Cliente Bcliente = repoCliente.BuscarCLiente(cedula);
		if (Bcliente != null) {
			String nombre=Bcliente.getNombre();
			ArrayList<DetalleFactura> lista=repoDetallaFactura.BuscarDetalleFactura(cedula);
			model.addAttribute("productos", lista);
			System.out.println(lista.size());
			if(lista.size()!=0) {
				model.addAttribute("preciototal", lista.get(0).getFactura().getPrecioTotal());
				model.addAttribute("preciototalIva", (lista.get(0).getFactura().getPrecioTotal())*1.19);
				model.addAttribute("idFactura", lista.get(0).getFactura().getIdFactura());
			}
			model.addAttribute("nombre", nombre);
			return "MiCarrito";
		}else {
			return "index";
		}
		
	}
	@GetMapping("/VerInfoModal/{id}")
	public String MostrarModal(@PathVariable("id") Integer id,Producto pro,Model model) {
		Producto prod=repoProducto.buscarProductoId(id);
		model.addAttribute("pro", prod.getNombre());
		return "indexClienteLogiado";
	}
	@GetMapping("/CerrarSeccion")
	public String cerrarSeccion() {
		cedula=0;
		return "redirect:/index";
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
				return "redirect:/Login";
			}
		} else {
			return "redirect:/index";
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

	@PostMapping(value="/Iniciar")
	public String BuscarLogin(@RequestParam Map <String, Object> params,Cliente cliente, DetalleFactura detalleFactura, BindingResult result, Model model) {

		Cliente Bcliente = repoCliente.login(cliente.getIdCliente(), cliente.getClave());
		if (Bcliente != null) {

			String nombre=Bcliente.getNombre();
			model.addAttribute("Cliente", Bcliente);
			model.addAttribute("detalleFactura",detalleFactura);
			cedula=cliente.getIdCliente();

			model.addAttribute("nombre", nombre);
			
			//PAGINACION
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
			return "redirect:/IndexCliente";
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
				return "redirect:/IndexCliente";
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
