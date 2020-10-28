package com.Avansada.controller;

import org.springframework.stereotype.Controller;

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
		DetalleFactura detalleFactura=new DetalleFactura();
		detalleFactura.setIdFactura(0);
		detalleFactura.setCantidad(cantidad);
		detalleFactura.setFactura(factura);
		detalleFactura.setProducto(producto);	
		repo.save(detalleFactura);
	}
	
	public void buscar(int idFactura) {
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
