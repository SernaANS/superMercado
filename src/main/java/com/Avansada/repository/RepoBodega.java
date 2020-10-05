package com.Avansada.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.Avansada.Modelo.Bodega;

public interface RepoBodega extends CrudRepository<Bodega,Integer>{

	@Query("SELECT A FROM Bodega A WHERE A.idBodega=?1")
	public Bodega buscarBodegaId(int idCategoria);
	
	@Query("SELECT A FROM Bodega A WHERE A.nombre=?1")
	public Bodega buscarBodegaNombre(String nombre);
	
	
	@Query("SELECT A FROM Bodega A")
	public ArrayList<Bodega> todasBodega();
}
