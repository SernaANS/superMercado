package com.Avansada.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.Avansada.Modelo.DetalleFactura;

public interface RepoDetallaFactura  extends  CrudRepository<DetalleFactura,Integer>{
	
	@Query("SELECT d FROM DetalleFactura d WHERE d.factura.cliente.idCliente=?1 and d.factura.fecha is null ")
	public ArrayList<DetalleFactura> BuscarDetalleFactura(int idCliente);
	
	@Query("SELECT d FROM DetalleFactura d WHERE d.factura.cliente.idCliente=?1 and d.factura.idFactura=?2")
	public ArrayList<DetalleFactura> buscarCedulaFactura(int idCliente,int idFactura);
	
	
	@Query("select d from DetalleFactura d where d.idFactura=?1")
	public DetalleFactura buscar(int id);
			
	@Query("select d from DetalleFactura d where d.factura.idFactura=?1")
	public DetalleFactura buscarporfactura(int id);
	
	
	@Query("select d from DetalleFactura d where d.factura.idFactura=?1")
	public ArrayList<DetalleFactura> buscarFactura(int id);
	
	@Query("select d from DetalleFactura d where d.producto.idProducto=?1")
	public ArrayList<DetalleFactura> buscarProductosFactura(int id);


	
	
	
}
