package com.Avansada.controller;

import java.io.IOException;
import java.util.ArrayList;
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

import com.Avansada.Modelo.DetalleBodega;
import com.Avansada.Modelo.DetalleFactura;
import com.Avansada.Modelo.Producto;
import com.Avansada.Modelo.Proveedor;
import com.Avansada.Modelo.Subcategoria;
import com.Avansada.repository.RepoDetallaFactura;
import com.Avansada.repository.RepoDetalleBodega;
import com.Avansada.repository.RepoProducto;
import com.Avansada.repository.RepoProvedor;
import com.Avansada.repository.RepoSubcategoria;
import com.cloudinary.utils.ObjectUtils;

@Controller
public class ControllerProducto {

	
    @Autowired
    com.Avansada.cloudinary.CloudinaryConfig cloudc;
	@Autowired
	private final RepoProducto repoProducto;
	@Autowired
	private final RepoSubcategoria reposubCategoria;
	@Autowired
	private final RepoProvedor repoProvedor;
	@Autowired
	private final RepoDetalleBodega repoDetalleBodega;
	
	private final RepoDetallaFactura RepoDetallaFactura;

    @Autowired
    public ControllerProducto(RepoProducto repoProducto,RepoSubcategoria reposubCategoria,RepoProvedor repoProvedor,RepoDetalleBodega repoDetalleBodega,RepoDetallaFactura repoDetallaFactura) {
		this.repoProducto = repoProducto;
		this.reposubCategoria=reposubCategoria;
		this.repoProvedor=repoProvedor;
		this.repoDetalleBodega=repoDetalleBodega;
		this.RepoDetallaFactura=repoDetallaFactura;
	}

	@GetMapping("/GestionProducto")
	public String showSignUpForm(Producto producto,Model model) {
		
		Iterable<Producto> lista = repoProducto.findAll();
		model.addAttribute("producto", producto);
		model.addAttribute("productolist", lista);

		
		//Para cargar el combobox categorias y provervdor
		Iterable<Subcategoria> lista2 = reposubCategoria.todasSubcategoria();
		model.addAttribute("subcategoria", lista2);
		Iterable<Proveedor> lista3 = repoProvedor.todosProveedores();
		model.addAttribute("proveedor", lista3);
		return "GestionProducto";
		
	}
	
	@GetMapping("/GestionProductoVendendor")
	public String GestionProductoVendendor(Producto producto,Model model) {
		
		Iterable<Producto> lista = repoProducto.findAll();
		model.addAttribute("producto", producto);
		model.addAttribute("productolist", lista);

		
		//Para cargar el combobox categorias y provervdor
		Iterable<Subcategoria> lista2 = reposubCategoria.todasSubcategoria();
		model.addAttribute("subcategoria", lista2);
		Iterable<Proveedor> lista3 = repoProvedor.todosProveedores();
		model.addAttribute("proveedor", lista3);
		return "GestionProductoVendendor";
		
	}
	
	
	@PostMapping("/RegistrarProducto")
	public String RegistarProducto(@Validated Producto producto, @RequestParam("file")MultipartFile file,BindingResult result, Model model) throws IOException {
		if (result.hasErrors()) {
			
			model.addAttribute("producto", producto);
			
			Iterable<Subcategoria> lista2 = reposubCategoria.todasSubcategoria();
			model.addAttribute("subcategoria", lista2);
			Iterable<Proveedor> lista3 = repoProvedor.todosProveedores();
			model.addAttribute("proveedor", lista3);
			
			return "GestionProducto";
		}
		if (file.isEmpty()) {
	        return "GestionProducto";
		  
        }
        try {
	        
        	//se crea la url del archivofoto
           Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            
           // Map uploadResult = cloudc.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
			
    		//Se inserta la direcion que se obtuvo del cloudc
    		
    		producto.setFoto(uploadResult.get("url").toString());
    		repoProducto.save(producto);
    		model.addAttribute("producto", repoProducto.findAll());
    		return "redirect:/GestionProducto";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/GestionProducto";
        }


	}

	@PostMapping("/ModificarProducto")
	public String ModificarProducto(@Validated Producto producto,@RequestParam("file")MultipartFile file, BindingResult result,Model model) {
		
		if (result.hasErrors()) {
			
			return "redirect:/GestionProducto";
		}
		
		if (producto!=null) {
			Producto buscaProducto = repoProducto.buscarProductoId(producto.getIdProducto());
			if (buscaProducto!=null) {
				
				//se crea la url del archivofoto
				try {
					Map uploadResult = cloudc.upload(file.getBytes(),
		                    ObjectUtils.asMap("resourcetype", "auto"));
					
					producto.setFoto(uploadResult.get("url").toString());
					buscaProducto.setNombre(producto.getNombre());
					buscaProducto.setDecripcion(producto.getDecripcion());
					buscaProducto.setPrecioCompraUnidad(producto.getPrecioCompraUnidad());
					buscaProducto.setPrecioVentaUnidad(producto.getPrecioVentaUnidad());
					
					buscaProducto.setFoto(producto.getFoto());
				} catch (IOException e) {
					// TODO: handle exception
				}
	            
				
				
				repoProducto.save(buscaProducto);
				System.out.println("entro modficar segundo if  final "+buscaProducto.getNombre());
				model.addAttribute("Mensaje", "logrado");
				return "redirect:/GestionProducto";
			}else {
				return "redirect:/GestionProducto";
			}
		}else {
			return "redirect:/GestionProducto";
		}
		

	}

	@PostMapping("/BuscarProducto")
	public String BuscarProducto(Producto producto, BindingResult result, Model model) {
		Producto buscaProducto = repoProducto.buscarProductoId(producto.getIdProducto());
		
		if (buscaProducto != null) {
			
			model.addAttribute("producto", buscaProducto);
			
			Iterable<Subcategoria> lista2 = reposubCategoria.todasSubcategoria();
			model.addAttribute("subcategoria", lista2);
			Iterable<Proveedor> lista3 = repoProvedor.todosProveedores();
			model.addAttribute("proveedor", lista3);
			
			 return "GestionProducto";
			
		} else {
			return "GestionProducto";
		}

	}

	@GetMapping("/deleteProducto")
	public String EliminarProducto(@PathVariable("idProducto") int id,BindingResult result, Model model) {
		
		Producto producto = repoProducto.buscarProductoId(id);
		System.out.println("entro eliminar "+producto.getNombre());
		
		ArrayList<DetalleBodega> dt=repoDetalleBodega.buscarDetalleBodegaIdProducto(producto.getIdProducto());
		
		for (int i = 0; i < dt.size(); i++) {
			repoDetalleBodega.deleteById((dt.get(i).getProducto().getIdProducto()));
		}

		ArrayList<DetalleFactura> dtF=RepoDetallaFactura.buscarProductosFactura(producto.getIdProducto());
		for (int i = 0; i < dtF.size(); i++) {
			RepoDetallaFactura.deleteById(dtF.get(i).getIdFactura());
		}
		repoProducto.delete(producto);
		model.addAttribute("producto", repoProducto.findAll());
		
		
		return "redirect:/GestionProducto";
	}

	@RequestMapping(value = "/Producto", method = RequestMethod.POST)
	public String handlePost(@RequestParam(required = false, value = "Registrar") String registrar,
			@RequestParam(required = false, value = "Modificar") String modificar,
			@RequestParam(required = false, value = "Buscar") String Buscar, 
			@RequestParam(required = false, value = "Eliminar") String Eliminar,
			@Validated Producto pruducto,BindingResult result, Model model,@RequestParam("file") MultipartFile file) throws IOException {

		if ("Registrar".equals(registrar)) {
			return RegistarProducto(pruducto,file, result, model);
		} else if ("Modificar".equals(modificar)) {
			return ModificarProducto(pruducto,file, result, model);
		}else if ("Eliminar".equals(Eliminar)) {
			return EliminarProducto(pruducto.getIdProducto(), result, model);
		}else if ("Buscar".equals(Buscar)) {
			return BuscarProducto(pruducto,result, model);
		}
		return "GestionProducto";
	}
	
	
	//------- vendeor -----------
	
	@RequestMapping(value = "/ProductoV", method = RequestMethod.POST)
	public String handlePostV(@RequestParam(required = false, value = "Registrar") String registrar,
			@RequestParam(required = false, value = "Modificar") String modificar,
			@RequestParam(required = false, value = "Buscar") String Buscar, 
			@RequestParam(required = false, value = "Eliminar") String Eliminar,
			@Validated Producto pruducto,BindingResult result, Model model,@RequestParam("file") MultipartFile file) throws IOException {

		if ("Registrar".equals(registrar)) {
			return RegistarProductoV(pruducto,file, result, model);
		} else if ("Modificar".equals(modificar)) {
			return ModificarProductoV(pruducto,file, result, model);
		}else if ("Eliminar".equals(Eliminar)) {
			return EliminarProductoV(pruducto.getIdProducto(), result, model);
		}else if ("Buscar".equals(Buscar)) {
			return BuscarProductoV(pruducto,result, model);
		}
		return "GestionProducto";
	}
	
	@PostMapping("/RegistrarProductoV")
	public String RegistarProductoV(@Validated Producto producto, @RequestParam("file")MultipartFile file,BindingResult result, Model model) throws IOException {
		if (result.hasErrors()) {
			
			model.addAttribute("producto", producto);
			
			Iterable<Subcategoria> lista2 = reposubCategoria.todasSubcategoria();
			model.addAttribute("subcategoria", lista2);
			Iterable<Proveedor> lista3 = repoProvedor.todosProveedores();
			model.addAttribute("proveedor", lista3);
			
			return "GestionProductoVendedor";
		}
		if (file.isEmpty()) {
	        return "GestionProductoVendedor";
		  
        }
        try {
	        
        	//se crea la url del archivofoto
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
			
    		//Se inserta la direcion que se obtuvo del cloudc
    		
    		producto.setFoto(uploadResult.get("url").toString());
    		repoProducto.save(producto);
    		model.addAttribute("producto", repoProducto.findAll());
    		return "redirect:/GestionProductoVendedor";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/GestionProductoVendedor";
        }


	}

	@PostMapping("/ModificarProductoV")
	public String ModificarProductoV(@Validated Producto producto,@RequestParam("file")MultipartFile file, BindingResult result,Model model) {
		
		if (result.hasErrors()) {
			
			return "redirect:/GestionProductoVendedor";
		}
		
		if (producto!=null) {
			Producto buscaProducto = repoProducto.buscarProductoId(producto.getIdProducto());
			if (buscaProducto!=null) {
				
				//se crea la url del archivofoto
				try {
					Map uploadResult = cloudc.upload(file.getBytes(),
		                    ObjectUtils.asMap("resourcetype", "auto"));
					
					producto.setFoto(uploadResult.get("url").toString());
					buscaProducto.setNombre(producto.getNombre());
					buscaProducto.setDecripcion(producto.getDecripcion());
					buscaProducto.setPrecioCompraUnidad(producto.getPrecioCompraUnidad());
					buscaProducto.setPrecioVentaUnidad(producto.getPrecioVentaUnidad());
					
					buscaProducto.setFoto(producto.getFoto());
				} catch (IOException e) {
					// TODO: handle exception
				}
	            
				
				
				repoProducto.save(buscaProducto);
				System.out.println("entro modficar segundo if  final "+buscaProducto.getNombre());
				model.addAttribute("Mensaje", "logrado");
				return "redirect:/GestionProductoVendedor";
			}else {
				return "redirect:/GestionProductoVendedor";
			}
		}else {
			return "redirect:/GestionProductoVendedor";
		}
		

	}

	@PostMapping("/BuscarProductoV")
	public String BuscarProductoV(Producto producto, BindingResult result, Model model) {
		Producto buscaProducto = repoProducto.buscarProductoId(producto.getIdProducto());
		
		if (buscaProducto != null) {
			
			model.addAttribute("producto", buscaProducto);
			
			Iterable<Subcategoria> lista2 = reposubCategoria.todasSubcategoria();
			model.addAttribute("subcategoria", lista2);
			Iterable<Proveedor> lista3 = repoProvedor.todosProveedores();
			model.addAttribute("proveedor", lista3);
			
			 return "GestionProductoVendedor";
			
		} else {
			return "GestionProductoVendedor";
		}

	}

	@GetMapping("/deleteProductoV")
	public String EliminarProductoV(@PathVariable("idProducto") int id,BindingResult result, Model model) {
		
		Producto producto = repoProducto.buscarProductoId(id);
		System.out.println("entro eliminar "+producto.getNombre());
		
		
		ArrayList<DetalleBodega> dt=repoDetalleBodega.buscarDetalleBodegaIdProducto(producto.getIdProducto());
		for (int i = 0; i < dt.size(); i++) {
			repoDetalleBodega.deleteById((dt.get(i).getProducto().getIdProducto()));
		}

		ArrayList<DetalleFactura> dtF=RepoDetallaFactura.buscarProductosFactura(producto.getIdProducto());
		
		for (int i = 0; i < dtF.size(); i++) {
			RepoDetallaFactura.deleteById(dtF.get(i).getIdFactura());
		}

		repoProducto.delete(producto);
		model.addAttribute("producto", repoProducto.findAll());
		
		
		return "redirect:/GestionProductoVendedor";
	}

}
