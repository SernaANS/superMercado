package com.Avansada.repository;

import java.util.ArrayList;
import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Avansada.Modelo.Proveedor;




@Repository
public interface RepoProvedor extends CrudRepository<Proveedor,Integer>{
	
	
	@Query("SELECT A FROM Proveedor A WHERE A.idProveedor=?1")
	public Proveedor buscarProveedor(int idProvedor);
	
	@Query("SELECT A FROM Subcategoria A WHERE A.categoria.id=?1")
	public List<Proveedor> buscarSubCategoria(int subcategoria);
	
	@Query("SELECT A FROM Subcategoria A")
	public ArrayList<Proveedor> todasSubCategoria();
	
	@Query("SELECT A FROM Proveedor A")
	public ArrayList<Proveedor> todosProveedores();

}
