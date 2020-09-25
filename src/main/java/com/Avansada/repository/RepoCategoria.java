package com.Avansada.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Avansada.Modelo.Categoria;



@Repository
public interface RepoCategoria extends CrudRepository<Categoria,Integer>{
	@Query("SELECT A FROM Categoria A WHERE A.idCategoria=?1")
	public Categoria buscarCategoriaId(int idCategoria);
	
	@Query("SELECT A FROM Categoria A WHERE A.nombre=?1")
	public Categoria buscarCategoriaNombre(String nombre);
	
	
	@Query("SELECT A FROM Categoria A")
	public ArrayList<Categoria> todasCategoria();

}
