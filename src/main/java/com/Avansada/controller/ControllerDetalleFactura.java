package com.Avansada.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import com.Avansada.Modelo.Cliente;
import com.Avansada.Modelo.DetalleFactura;
import com.Avansada.Modelo.Factura;
import com.Avansada.Modelo.Producto;
import com.Avansada.repository.RepoDetallaFactura;

@Controller
public class ControllerDetalleFactura {
	
	RepoDetallaFactura repo;

	public ControllerDetalleFactura(RepoDetallaFactura repo) {
		super();
		this.repo = repo;
	}
	
	
	public void registrar(Producto producto, Factura factura,Integer cantidad) {
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
			repo.eliminarDetalle(cedulaCliente);
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
	

	

}
