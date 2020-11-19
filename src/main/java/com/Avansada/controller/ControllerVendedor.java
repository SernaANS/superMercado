package com.Avansada.controller;

import java.io.IOException;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;

import com.Avansada.Modelo.Admin;
import com.Avansada.Modelo.Cliente;
import com.Avansada.Modelo.Factura;
import com.Avansada.Modelo.Producto;
import com.Avansada.Modelo.Proveedor;
import com.Avansada.Modelo.Vendedor;
import com.Avansada.repository.RepoFactura;
import com.Avansada.repository.RepoProducto;
import com.Avansada.repository.RepoVendedor;
import com.cloudinary.utils.ObjectUtils;

@Controller
public class ControllerVendedor {
	
	public static Admin admin=new Admin("1212","1212","sernita@gamil.com","santiago"); 
	
	@Autowired
    com.Avansada.cloudinary.CloudinaryConfig cloudc;
	
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
	private final RepoFactura repoFactura;

	@Autowired
	private final RepoProducto repoProducto;
	
	@Autowired
	public ControllerVendedor(RepoVendedor repoVendedor, RepoFactura repoFactura,RepoProducto repoProducto) {
		super();
		this.repoVendedor = repoVendedor;
		this.repoFactura= repoFactura;
		this.repoProducto = repoProducto;
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
	@GetMapping("/GestionVendedor")
	public String GesVendedor(Vendedor vendedor, Model model) {
		Iterable<Vendedor> lista = repoVendedor.findAll();
		model.addAttribute("lista", lista);
		
		model.addAttribute("vendedor", vendedor);
		return "GestionVendedor";
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
	public String RegistarVendedor(Vendedor vendedor,@RequestParam("file")MultipartFile file,
			BindingResult result, Model model) throws IOException {
		if (result.hasErrors()) {
			return "GestionVendedor";
		}
		if (vendedor.getIdVendedor() >= 0 || !vendedor.getNombre().isEmpty() || vendedor.getDireccion() != ""
				|| vendedor.getApellido() != "" || vendedor.getTelefono() != "" 
				 || vendedor.getFechaNacimiento() != null) {
			if (BuscarVendedor(vendedor, result, model) != null) {
				
				//se crea la url del archivofoto
	            Map uploadResult = cloudc.upload(file.getBytes(),
	                    ObjectUtils.asMap("resourcetype", "auto"));
				
	    		//Se inserta la direcion que se obtuvo del cloudc
	    		
	    		vendedor.setFoto(uploadResult.get("url").toString());
				
				repoVendedor.save(vendedor);
				return "redirect:/Login";
			} else {
				return "redirect:/GestionCategoria";
			}
		} else {
			return "redirect:/GestionCategoria";
		}

	}

	public String BuscarVendedor(Vendedor vendedor, BindingResult result, Model model) {

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
		
		if(Integer.toString(vendedor.getIdVendedor()).equals(admin.getIdAdmin())&&vendedor.getClave().equals(admin.getClave())) {
			return "IndexAdminLogiado";
		}else {
			Vendedor Bvendedor = repoVendedor.loginVendedor(vendedor.getIdVendedor());
			if (Bvendedor != null) {
				cedula=vendedor.getIdVendedor();
				Iterable<Factura> listaProductos=repoFactura.facturasVendedor(cedula);
				if(listaProductos!=null) {
					model.addAttribute("facturas",listaProductos);
					return "indexVendedorLogiado";
				}
				
				return "indexVendedorLogiado";
			} else {
				return "/Index";
			}
		}
	}
	
	@GetMapping("/MostrarFracturaV/{idFactura}")
	public String MostrarFactura(@PathVariable("idFactura") Integer id,Factura factura,Model model) {
		//Detalles
		Factura f=repoFactura.buscarFacturaId(id);
		model.addAttribute("factura",f);
	
		
		return "VerFacturaV";
	}

	public String EliminarVendedor(Vendedor vendedor, BindingResult result, Model model) {
		Vendedor bCliente = repoVendedor.findById(vendedor.getIdVendedor())
				.orElseThrow(() -> new IllegalArgumentException("Invalid usuario Id:" + vendedor.getIdVendedor()));
		repoVendedor.delete(vendedor);
		return "redirect:/Index";
	}

	public String ModificarVendedor(Vendedor vendedor,@RequestParam("file")MultipartFile file,
			BindingResult result, Model model) {
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
			@Validated Vendedor vendedor, BindingResult result, Model model,
			@RequestParam("file") MultipartFile file) throws IOException {
		
		if ("Modificar".equals(modificar)) {
			return ModificarVendedor(vendedor,file, result, model);
		}else if ("Eliminar".equals(eliminar)) {
			return EliminarVendedor(vendedor, result, model);
		}
		
		return "index";
	}

	@RequestMapping(value = "/RegistroV", method = RequestMethod.POST)
	public String BottonFormulario(@RequestParam(required = false, value = "RegistrarV") String registrar,
			@Validated Vendedor vendedor, BindingResult result, Model model,
			@RequestParam("file") MultipartFile file) throws IOException {

		if ("RegistrarV".equals(registrar)) {
			return RegistarVendedor(vendedor,file, result, model);
		}

		return "/Index";

	}

	@RequestMapping(value = "/Vendedor", method = RequestMethod.POST)
	public String handlePost(@RequestParam(required = false, value = "Registrar") String registrar,
			@RequestParam(required = false, value = "Modificar") String modificar,
			@RequestParam(required = false, value = "Buscar") String Buscar, 
			@RequestParam(required = false, value = "Eliminar") String Eliminar, 
			@Validated Vendedor provedor,BindingResult result, Model model,
			@RequestParam("file") MultipartFile file) throws IOException {

		if ("Registrar".equals(registrar)) {
			return RegistarVendedor(provedor,file, result, model);
		} else if ("Modificar".equals(modificar)) {
			return ModificarVendedor(provedor,file, result, model);
		}else if ("Eliminar".equals(Eliminar)) {
			return EliminarVendedor(provedor, result, model);
		}else if ("Buscar".equals(Buscar)) {
			return BuscarVendedor(provedor, result, model);
		}
		return "GestionProvedor";
	}

}
