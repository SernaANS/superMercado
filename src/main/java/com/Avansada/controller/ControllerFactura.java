package com.Avansada.controller;

import java.util.ArrayList;
import java.util.Date;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
import com.Avansada.Modelo.Factura;
import com.Avansada.Modelo.Producto;
import com.Avansada.Modelo.Vendedor;
import com.Avansada.repository.RepoCliente;
import com.Avansada.repository.RepoDetallaFactura;
import com.Avansada.repository.RepoFactura;
import com.Avansada.repository.RepoProducto;
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
	private final RepoDetallaFactura repoDTfactura;
	

	ControllerDetalleFactura controlador;
	
	
	
	 @Autowired
	    public ControllerFactura(RepoProducto repoProducto,RepoFactura repoFactura,RepoCliente repoCliente,RepoVendedor repoVendedor,
	    		RepoDetallaFactura repoDTfactura) {
			this.repoProducto = repoProducto;
			this.repoFactura=repoFactura;
			this.repoCliente=repoCliente;
			this.repoVendedor=repoVendedor;
			this.repoDTfactura=repoDTfactura;
		}

	 @GetMapping("/RegistrarFactura/{idFactura}")
		public String RegistarFactura( @PathVariable("idFactura") Integer id,Model model) {
			
				if(ControllerCliente.cedula!=0) {
					Cliente cliente= new Cliente();
					cliente.setIdCliente(ControllerCliente.getCedula());
					ArrayList<Vendedor> listaVendedores=(ArrayList<Vendedor>) repoVendedor.findAll();
					System.out.println("esta la tiene id"+id);
					int numero = (int) (Math.random() * listaVendedores.size());
					Vendedor vende=listaVendedores.get(numero);
					ArrayList<Producto> listaProductos=ControllerCliente.misProductos;
					Factura facturita=repoFactura.buscarFacturaId(id);
					 Date ahora = new Date();
					 System.out.println(ahora);
					 
					 
					 ArrayList<DetalleFactura> detallito=repoDTfactura.buscarFactura(id);
					 System.out.println("este es detallito"+detallito.size());
					 if(detallito!=null) {
						 facturita.setFecha(ahora);
							facturita.setVendedor(vende);		 
							repoFactura.save(facturita);
							 return "redirect:/MisFacturas";
					 }
					 return "redirect:/IndexCliente";
					
				}else {
					return "MisFacturas";
				}
				
		}

		
		//Este Funciona con ID
		public String BuscarFacturaId(int id, Model model) {
			Factura buscaFactura = repoFactura.buscarFacturaId(id);
			if (buscaFactura != null) {
				model.addAttribute("factura", buscaFactura.getIdFactura());
				return "redirect:/";		
			} else {
				return  "algo";
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
				
			}else if ("Eliminar".equals(Eliminar)) {
				return EliminarFactura(factura.getIdFactura(), result, model);
			}else if ("Buscar".equals(Buscar)) {
				return BuscarFacturaId(factura.getIdFactura(), model);
			}
			return "MiCarrito";
		}
		
		@GetMapping("/MostrarFractura/{id}")
		public String MostrarFactura(Factura factura,Model model) {
			//Detalles
			Iterable<Producto > listaProductos=repoProducto.findAll();
			model.addAttribute("productos",listaProductos);
			
			return "VerFactura";
		}
		
		@GetMapping("/MisFacturas")
		public String MisFacturas(Model model) {
			if(ControllerCliente.getCedula()!=0) {
				
				Iterable<Factura> listaProductos=repoFactura.historialFacturas(ControllerCliente.getCedula());
				if(listaProductos!=null) {
					model.addAttribute("facturas",listaProductos);
				}
				
				return "MisFacturas";
			}else {		
				return "MisFacturas";
			}

		}
		

	 
	 
	 
}	
