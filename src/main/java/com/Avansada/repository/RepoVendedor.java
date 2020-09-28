package com.Avansada.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.Avansada.Modelo.Vendedor;

public interface RepoVendedor  extends CrudRepository<Vendedor,Integer>{

	@Query("SELECT c FROM Vendedor c WHERE c.idVendedor=?1")
	public Vendedor BuscarVendedor(int idVendedor);
	
	@Query("select c from Vendedor c where c.idVendedor=?1")
	public Vendedor loginVendedor(int idVendedor);
	
	
	@Query("SELECT v FROM Vendedor v")
	public ArrayList<Vendedor> todosVendedor();
}
