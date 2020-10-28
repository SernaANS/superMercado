package com.Avansada.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.Avansada.Modelo.Cliente;
import com.Avansada.Modelo.DetalleFactura;

public interface RepoDetallaFactura  extends  CrudRepository<DetalleFactura,Integer>{
	
	@Query("SELECT d FROM DetalleFactura d WHERE d.factura.cliente.idCliente=?1")
	public DetalleFactura BuscarDetalleFactura(int idCliente);
	
}
