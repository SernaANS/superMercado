package com.Avansada.repository;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.Avansada.Modelo.Factura;


public interface RepoFactura extends CrudRepository<Factura,Integer>{

	@Query("SELECT A FROM Factura A WHERE A.idFactura=?1")
	public Factura buscarFacturaId(int idFactura);
	
	@Query("SELECT A FROM Factura A WHERE A.cliente.idCliente=?1")
	public ArrayList<Factura> lista(int cedula);
	

	@Query("SELECT A FROM Factura A WHERE A.cliente.idCliente=?1 and A.fecha is not null")
	public ArrayList<Factura> historialFacturas(int cedula);
}
