package com.Avansada.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.Avansada.Modelo.Cliente;
import com.Avansada.Modelo.Proveedor;


public interface RepoCliente  extends CrudRepository<Cliente,Integer> {
	
	@Query("SELECT c FROM Cliente c WHERE c.idCliente=?1")
	public Cliente BuscarCLiente(int idCliente);
	
	@Query("select c from Cliente c where c.idCliente=?1 AND c.clave=?2")
	public Cliente login(int idCliente,String contrasena);
	
	
	@Query("SELECT c FROM Cliente c")
	public ArrayList<Cliente> todosClientes();

}
