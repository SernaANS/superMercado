package com.Avansada.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.Avansada.Modelo.Bodega;
import com.Avansada.Modelo.DetalleBodega;

public interface RepoDetalleBodega extends CrudRepository<DetalleBodega,Integer>{

	@Query("SELECT A FROM DetalleBodega A WHERE A.idDetalleBodega=?1")
	public DetalleBodega buscarDetalleBodegaId(int idDetalleBodega);
	
	@Query("SELECT A FROM Bodega A WHERE A.nombre=?1")
	public DetalleBodega buscarDetalleBodegaNombre(String nombre);
	
	
	@Query("SELECT A FROM Bodega A")
	public ArrayList<DetalleBodega> todasDetalleBodega();
}