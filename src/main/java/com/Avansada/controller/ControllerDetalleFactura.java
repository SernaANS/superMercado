package com.Avansada.controller;

import java.util.ArrayList;

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
import com.Avansada.repository.RepoCliente;
import com.Avansada.repository.RepoDetallaFactura;
import com.Avansada.repository.RepoFactura;
import com.Avansada.repository.RepoProducto;

@Controller
public class ControllerDetalleFactura {
	
	RepoDetallaFactura repo;
	RepoProducto repoProducto;
	RepoCliente repoCliente;
	RepoFactura repoFactura;
	public static Factura factura;
	

	
	
	public ControllerDetalleFactura(RepoDetallaFactura repo,RepoProducto repoProducto,RepoCliente repoCliente,RepoFactura repoFactura) {
		super();
		this.repo = repo;
		this.repoProducto=repoProducto;
		this.repoCliente=repoCliente;
		this.repoFactura=repoFactura;
	
	}
	
	
	
	@GetMapping("/AgregarMiCarrito/{id}")
	public String registrarDetalle(@PathVariable("id") Integer id,Model model) {
		
		if(ControllerCliente.getCedula()==0) {
			return "redirect:/index";
		}
		
		ArrayList<Factura> lista=repoFactura.lista(ControllerCliente.getCedula());
		if(lista.size()==0) {
			factura=new Factura();
			Cliente cliente=repoCliente.BuscarCLiente(ControllerCliente.getCedula());
			factura.setCliente(cliente);
			repoFactura.save(factura);
		}
		
		ArrayList<Factura> listaBusqueda=repoFactura.lista(ControllerCliente.getCedula());
		
		for (int i = 0; i < listaBusqueda.size(); i++) {
			if(listaBusqueda.get(i).getIdFactura()!=0 && listaBusqueda.get(i).getFecha()==null) {
				DetalleFactura newDetalle=new DetalleFactura();
				Producto producto=repoProducto.buscarProductoId(id);
				newDetalle.setProducto(producto);
				newDetalle.setFactura(listaBusqueda.get(i));
				repo.save(newDetalle);
				
			    System.out.println(listaBusqueda.size());
			}else if(i+1 == listaBusqueda.size()){
				factura=new Factura();
				Cliente cliente=repoCliente.BuscarCLiente(ControllerCliente.getCedula());
				factura.setCliente(cliente);
				repoFactura.save(factura);
				DetalleFactura newDetalle=new DetalleFactura();
				Producto producto=repoProducto.buscarProductoId(id);
				newDetalle.setProducto(producto);
				newDetalle.setFactura(listaBusqueda.get(i));
				repo.save(newDetalle);
			    System.out.println(cliente.getApellido());
			    System.out.println(listaBusqueda.size());
			}
			
			
		}
		return "redirect:/IndexCliente";
	}
	
	public ArrayList<DetalleFactura> listar(int cedulaCliente) {
		
		ArrayList<DetalleFactura> detalleFactura=repo.BuscarDetalleFactura(cedulaCliente);
		if(detalleFactura!=null) {
			return detalleFactura;
		}else {
			return null;
		}
		
	}
	
     public ArrayList<DetalleFactura> buscarCedulaFactura(int cedula,int idFactura) {
		
    	 ArrayList<DetalleFactura> detalleFactura=repo.buscarCedulaFactura(cedula, idFactura);
		if(detalleFactura!=null) {
			return detalleFactura;
		}else {
			return null;
		}
		
	}
	
	
	
	
	public void eliminar(Integer cedulaCliente) {
		
		if(cedulaCliente!=0) {
			
		}
		
	}
	
	public void modificar(Producto producto, Factura factura,Integer cantidad) {
		if(producto!=null && factura!=null && cantidad!=0) {
			DetalleFactura newDetalleFactura=new DetalleFactura();
			newDetalleFactura.setCantidad(cantidad);
			newDetalleFactura.setDespachos(null);
			newDetalleFactura.setFactura(factura);
			newDetalleFactura.setProducto(producto);
			repo.save(newDetalleFactura);
		}else {
		 String mensaje="fallo";	
		 System.out.println(mensaje);
		}
		
	}
	
	//bottones
	
	@RequestMapping(value = "/AgregarMicarrito", method = RequestMethod.POST)
	public String BottonFormulario(@RequestParam(required = false, value = "Añadir") String Añadir,
			@Validated DetalleFactura detalleFactura, BindingResult result, Model model) {

		if ("Añadir".equals(Añadir)) {
			
		}

		return "/Index";

	}

	

}
