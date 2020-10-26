package com.Avansada.controller;

import java.util.ArrayList;
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
import com.Avansada.Modelo.Cliente;
import com.Avansada.Modelo.DetalleBodega;
import com.Avansada.Modelo.Factura;
import com.Avansada.Modelo.Producto;
import com.Avansada.Modelo.Vendedor;
import com.Avansada.repository.RepoCliente;
import com.Avansada.repository.RepoFactura;
import com.Avansada.repository.RepoProducto;
import com.Avansada.repository.RepoProvedor;
import com.Avansada.repository.RepoSubcategoria;
import com.Avansada.repository.RepoVendedor;

@Controller
public class ControllerFactura {
	
	@Autowired
	private final RepoProducto repoProducto;
	@Autowired
	private final RepoFactura repoFactura;
	
	@Autowired
	private final RepoCliente repoCliente;
	@Autowired
	private final RepoVendedor repoVendedor;
	
	 @Autowired
	    public ControllerFactura(RepoProducto repoProducto,RepoFactura repoFactura,RepoCliente repoCliente,RepoVendedor repoVendedor) {
			this.repoProducto = repoProducto;
			this.repoFactura=repoFactura;
			this.repoCliente=repoCliente;
			this.repoVendedor=repoVendedor;
		}

	 @PostMapping("/RegistrarFactura")
		public String RegistarFactura( BindingResult result, Model model) {
			if (result.hasErrors()) {
				return "algo";
			}
				if(ControllerCliente.cedula!=0) {
					Cliente cliente= new Cliente();
					cliente.setIdCliente(ControllerCliente.getCedula());
					ArrayList<Vendedor> listaVendedores=(ArrayList<Vendedor>) repoVendedor.findAll();
					int numero = (int) (Math.random() * listaVendedores.size()) + 1;
					Vendedor vende=listaVendedores.get(numero);
					ArrayList<Producto> listaProductos=ControllerCliente.misProductos;
					int total=0;
					for (int i = 0; i < listaProductos.size(); i++) {
						total+=listaProductos.get(i).getPrecioVentaUnidad();
					}
					 Date ahora = new Date();
					 System.out.println(ahora);
					 
					 Factura facturita= new Factura(0, ahora, total, cliente, vende);
					 repoFactura.save(facturita);
					 return "algo";
				}else {
					return "redirect:/Login";
				}
				
		}

		
		//Este Funciona con ID
		public String BuscarFacturaId(int id, Model model) {
			Factura buscaFactura = repoFactura.buscarFacturaId(id);
			if (buscaFactura != null) {
				model.addAttribute("factura", buscaFactura.getIdFactura());
				return "redirect:/";		
			} else {
				return "algo";
			}
		}
		
		@GetMapping("/deleteFactura")
		public String EliminarFactura(@PathVariable("id") Integer id,BindingResult result, Model model) {
			Factura factura = repoFactura.buscarFacturaId(id);
			repoFactura.delete(factura);
			return "redirect:/algo";
		}

		@RequestMapping(value = "/Factura", method = RequestMethod.POST)
		public String handlePost(
				@RequestParam(required = false, value = "Registrar") String registrar,
				@RequestParam(required = false, value = "Buscar") String Buscar, 
				@RequestParam(required = false, value = "Eliminar") String Eliminar, 
				@Validated Factura factura,BindingResult result, Model model) {

			if ("Registrar".equals(registrar)) {
				return RegistarFactura( result, model);
			}else if ("Eliminar".equals(Eliminar)) {
				return EliminarFactura(factura.getIdFactura(), result, model);
			}else if ("Buscar".equals(Buscar)) {
				return BuscarFacturaId(factura.getIdFactura(), model);
			}
			return "GestionBodega";
		}
		
		@GetMapping("/MostrarFractura/{id}")
		public String MostrarFactura(Factura factura,Model model) {
			//Detalles
			Iterable<Producto > listaProductos=repoProducto.findAll();
			model.addAttribute("productos",listaProductos);
			
			return "";
		}
		
		@GetMapping("/MisFacturas")
		public String MisFacturas(Model model) {
			if(ControllerCliente.getCedula()!=0) {
				Iterable<Factura> listaProductos=repoFactura.findAll();
				model.addAttribute("facturas",listaProductos);
				return "MisFacturas";
			}else {		
				return "MisFacturas";
			}

		}
	 
	 
	 
}	
