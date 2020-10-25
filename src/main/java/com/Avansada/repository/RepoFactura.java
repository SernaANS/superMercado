package com.Avansada.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.Avansada.Modelo.Factura;
import com.Avansada.Modelo.Proveedor;

public interface RepoFactura extends CrudRepository<Factura,Integer>{

	@Query("SELECT A FROM Factura A WHERE A.idFactura=?1")
	public Factura buscarFacturaId(int idFactura);
}
