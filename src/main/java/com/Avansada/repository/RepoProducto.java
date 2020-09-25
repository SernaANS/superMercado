package com.Avansada.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Avansada.Modelo.Producto;
import com.Avansada.Modelo.Proveedor;

@Repository
public interface RepoProducto extends CrudRepository<Producto,Integer>{
	
	@Query("SELECT A FROM Producto A WHERE A.idProducto=?1")
	public Producto buscarProductoId(int idProducto);
	
	@Query("SELECT A FROM Producto A WHERE A.nombre=?1")
	public Producto buscarProductoNombre(String nombre);
	
	@Query("SELECT A FROM Producto A")
	public ArrayList<Producto> todosProducto();

}
