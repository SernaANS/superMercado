package com.Avansada.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.Avansada.Modelo.Bodega;
import com.Avansada.Modelo.Despacho;

public interface RepoDespacho extends CrudRepository<Despacho,Integer>{

	@Query("SELECT A FROM Despacho A")
	public ArrayList<Despacho> todasDespacho();
}
