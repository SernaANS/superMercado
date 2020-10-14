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

import com.Avansada.Modelo.Cliente;
import com.Avansada.Modelo.Producto;
import com.Avansada.Modelo.Proveedor;
import com.Avansada.Modelo.Subcategoria;
import com.Avansada.Modelo.Vendedor;
import com.Avansada.repository.RepoVendedor;
import com.cloudinary.utils.ObjectUtils;

@Controller
public class ControllerVendedor {
	
    @Autowired
    com.Avansada.cloudinary.CloudinaryConfig cloudc;
    
	public static int idVendedor;
	
	public static int getCedula() {
		return idVendedor;
	}

	public static void setCedula(int cedula) {
		ControllerVendedor.idVendedor = cedula;
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

	@GetMapping("/GestionarVendedor")
	public String Formulario(Vendedor vendedor, Model model) {
		model.addAttribute("vendedor", vendedor);
		Iterable<Vendedor> lista = repoVendedor.findAll();
		model.addAttribute("Vendeddrolist", lista);
		return "GestionarVendedor";
	}

	@GetMapping("/MiVendedor")
	public String Miperfil(Vendedor vendedor, Model model) {
		Vendedor Bvendedor = repoVendedor.BuscarVendedor(idVendedor);
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

	
	@PostMapping("/RegistrarVendedor")

	public String RegistarVendedor(@Validated Vendedor vendedor, @RequestParam("file")MultipartFile file,BindingResult result, Model model) throws IOException {
		if (result.hasErrors()) {
			
			model.addAttribute("vendedor", vendedor);
			System.out.println("entro al error del result");
			return "GestionarVendedor";
		}
		if (file.isEmpty()) {
			System.out.println("entro al error del fille");
	        return "GestionarVendedor";
		  
        }
        try {
	        
        	//se crea la url del archivofoto
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
			
    		//Se inserta la direcion que se obtuvo del cloudc
    		
    		vendedor.setFoto(uploadResult.get("url").toString());
    		System.out.println("entro al antes de guardar");
    		repoVendedor.save(vendedor);
    		model.addAttribute("vendedor", repoVendedor.findAll());
    		return "redirect:/GestionarVendedor";
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("entro al catch");
            return "redirect:/GestionarVendedor";
        }


	}

	@PostMapping("/ModificarVendedor")
	public String ModificarVendedor(@Validated Vendedor vendedor,@RequestParam("file")MultipartFile file, BindingResult result,Model model) {
		
		if (result.hasErrors()) {
			
			return "redirect:/GestionVendedor";
		}
		
		if (vendedor!=null) {
			Vendedor buscaVendedor = repoVendedor.BuscarVendedor(vendedor.getIdVendedor());
			if (buscaVendedor!=null) {
				
				//se crea la url del archivofoto
				try {
					Map uploadResult = cloudc.upload(file.getBytes(),
		                    ObjectUtils.asMap("resourcetype", "auto"));
					
					vendedor.setFoto(uploadResult.get("url").toString());
					buscaVendedor.setNombre(vendedor.getNombre());
					buscaVendedor.setApellido(vendedor.getApellido());
					buscaVendedor.setDireccion(vendedor.getDireccion());
					buscaVendedor.setTelefono(vendedor.getTelefono());
					
					
					buscaVendedor.setFoto(vendedor.getFoto());
				} catch (IOException e) {
					// TODO: handle exception
				}
	            
				
				
				repoVendedor.save(buscaVendedor);
				System.out.println("entro modficar segundo if  final "+buscaVendedor.getNombre());
				model.addAttribute("Mensaje", "logrado");
				return "redirect:/GestionVendedor";
			}else {
				return "redirect:/GestionVendedor";
			}
		}else {
			return "redirect:/GestionVendedor";
		}
		

	}

	@PostMapping("/BuscarVendedor")
	public String BuscarVendedor(Vendedor vendedor, BindingResult result, Model model) {
		Vendedor buscaVendedor = repoVendedor.BuscarVendedor(vendedor.getIdVendedor());
		
		if (buscaVendedor != null) {
			
			model.addAttribute("vendedor", buscaVendedor);
			
			 return "GestionVendedor";
			
		} else {
			return "GestionVendedor";
		}

	}

	@GetMapping("/deleteVendedor")
	public String EliminarVendedor(@PathVariable("idVendedor") int id,BindingResult result, Model model) {
		
		Vendedor vender = repoVendedor.BuscarVendedor(id);
		System.out.println("entro eliminar "+vender.getNombre());

		repoVendedor.delete(vender);
		model.addAttribute("vendedor", repoVendedor.findAll());
		return "redirect:/GestionProducto";
	}

	public String BuscarCedula(Vendedor vendedor, BindingResult result, Model model) {

		Vendedor Bvendedor = repoVendedor.BuscarVendedor(idVendedor);
		if (Bvendedor != null) {
			model.addAttribute("vendedor", Bvendedor);
			return "MiPerfil";

		} else {
			return "indexVendedorLogiado";
		}

	}
	


	@PostMapping("/IniciarV/")
	public String Login(Vendedor vendedor, BindingResult result, Model model) {

		Vendedor Bvendedor = repoVendedor.loginVendedor(vendedor.getIdVendedor());
		if (Bvendedor != null) {
			model.addAttribute("Vendedor", Bvendedor);
			idVendedor=vendedor.getIdVendedor();
			return "indexVendedorLogiado";
		} else {
			return "/Index";
		}

	}

	



	public void listar(Vendedor vendedor, BindingResult result, Model model) {
		Iterable<Vendedor> lista = repoVendedor.findAll();
		model.addAttribute("lista", lista);
	}
	////////////////////////// Bottones///////////////////////////////////////////

/**

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
**/
	
	@RequestMapping(value = "/Vendedor", method = RequestMethod.POST)
	public String handlePost(@RequestParam(required = false, value = "Registrar") String registrar,
			@RequestParam(required = false, value = "Modificar") String modificar,
			@RequestParam(required = false, value = "Buscar") String Buscar, 
			@RequestParam(required = false, value = "Eliminar") String Eliminar,
			@Validated Vendedor vendedor,BindingResult result, Model model,@RequestParam("file") MultipartFile file) throws IOException {

		if ("Registrar".equals(registrar)) {
			return RegistarVendedor(vendedor,file, result, model);
		} else if ("Modificar".equals(modificar)) {
			return ModificarVendedor(vendedor,file, result, model);
		}else if ("Eliminar".equals(Eliminar)) {
			return EliminarVendedor(vendedor.getIdVendedor(), result, model);
		}else if ("Buscar".equals(Buscar)) {
			return BuscarVendedor(vendedor,result, model);
		}
		return "GestionVendedor";
	}

	@RequestMapping(value = "/Ingresar", method = RequestMethod.POST)
	public String BottonIngresar(@RequestParam(required = false, value = "Ingresar") String Ingresar,
			@Validated Vendedor vendedor, BindingResult result, Model model) {

		if ("Ingresar".equals(Ingresar)) {
			return Login(vendedor, result, model);
		}

		return "/Index";

	}

}
