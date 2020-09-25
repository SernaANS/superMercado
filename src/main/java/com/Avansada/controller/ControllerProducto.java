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


import com.Avansada.Modelo.Producto;
import com.Avansada.Modelo.Proveedor;
import com.Avansada.Modelo.Subcategoria;
import com.Avansada.repository.RepoProducto;
import com.Avansada.repository.RepoProvedor;
import com.Avansada.repository.RepoSubcategoria;

@Controller
public class ControllerProducto {

	@Autowired
	private final RepoProducto repoProducto;
	@Autowired
	private final RepoSubcategoria reposubCategoria;
	@Autowired
	private final RepoProvedor repoProvedor;

    @Autowired
    public ControllerProducto(RepoProducto repoProducto,RepoSubcategoria reposubCategoria,RepoProvedor repoProvedor) {
		this.repoProducto = repoProducto;
		this.reposubCategoria=reposubCategoria;
		this.repoProvedor=repoProvedor;
	}

	@GetMapping("/GestionProducto")
	public String showSignUpForm(Producto producto,Model model) {
		
<<<<<<< HEAD
		model.addAttribute("producto", producto);
		//Para cargar el combobox categorias
=======
		Iterable<Producto> lista = repoProducto.findAll();
		model.addAttribute("producto", producto);
		model.addAttribute("productolist", lista);

		
		//Para cargar el combobox categorias y provervdor
>>>>>>> a79c53e... En este se creo la interfas de producto y la funcionalidad del crud
		Iterable<Subcategoria> lista2 = reposubCategoria.todasSubcategoria();
		model.addAttribute("subcategoria", lista2);
		Iterable<Proveedor> lista3 = repoProvedor.todosProveedores();
		model.addAttribute("proveedor", lista3);
		return "GestionProducto";
		
	}
	
	
	@PostMapping("/RegistrarProducto")
	public String RegistarProducto(@Validated Producto producto, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("producto", producto);
			
			Iterable<Subcategoria> lista2 = reposubCategoria.todasSubcategoria();
			model.addAttribute("subcategoria", lista2);
			Iterable<Proveedor> lista3 = repoProvedor.todosProveedores();
			model.addAttribute("proveedor", lista3);
			
			return "GestionProducto";
		}
		
		repoProducto.save(producto);
		model.addAttribute("producto", repoProducto.findAll());

		return "redirect:/GestionProducto";

	}

	@PostMapping("/ModificarProducto")
	public String ModificarProducto(@Validated Producto producto, BindingResult result,Model model) {
		
		if (result.hasErrors()) {
			System.out.println("entro modificar primer if "+producto.getNombre());
			return "listarMensajeVendedor";
		}
		
		if (producto!=null) {
			Producto buscaProducto = repoProducto.buscarProductoId(producto.getIdProducto());
			if (buscaProducto!=null) {
				
				buscaProducto.setNombre(producto.getNombre());
				buscaProducto.setDecripcion(producto.getDecripcion());
				buscaProducto.setPrecioCompraUnidad(producto.getPrecioCompraUnidad());
				buscaProducto.setPrecioVentaUnidad(producto.getPrecioVentaUnidad());
				
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

		repoProducto.delete(producto);
		model.addAttribute("producto", repoProducto.findAll());
		return "redirect:/GestionProducto";
	}

	@RequestMapping(value = "/Producto", method = RequestMethod.POST)
	public String handlePost(@RequestParam(required = false, value = "Registrar") String registrar,
			@RequestParam(required = false, value = "Modificar") String modificar,
			@RequestParam(required = false, value = "Buscar") String Buscar, 
			@RequestParam(required = false, value = "Eliminar") String Eliminar, 
			@Validated Producto pruducto,BindingResult result, Model model) {

		if ("Registrar".equals(registrar)) {
			return RegistarProducto(pruducto, result, model);
		} else if ("Modificar".equals(modificar)) {
			return ModificarProducto(pruducto, result, model);
		}else if ("Eliminar".equals(Eliminar)) {
			return EliminarProducto(pruducto.getIdProducto(), result, model);
		}else if ("Buscar".equals(Buscar)) {
			return BuscarProducto(pruducto,result, model);
		}
		return "GestionSubcategoria";
	}
	
}
