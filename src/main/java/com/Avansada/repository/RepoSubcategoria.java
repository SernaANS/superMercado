package com.Avansada.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Avansada.Modelo.Subcategoria;

@Repository
public interface RepoSubcategoria extends CrudRepository<Subcategoria,Integer> {

	@Query("SELECT A FROM Subcategoria A WHERE A.idSubcategoria=?1")
	public Subcategoria buscarSubcategoriaId(int idCategoria);
	
	@Query("SELECT A FROM Subcategoria A WHERE A.nombre=?1")
	public Subcategoria buscarSubcategoriaNombre(String nombre);
	
	@Query("SELECT A FROM Subcategoria A")
	public ArrayList<Subcategoria> todasSubcategoria();
}
