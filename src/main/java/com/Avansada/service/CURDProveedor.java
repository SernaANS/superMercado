package com.Avansada.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.Avansada.Modelo.Proveedor;
import com.Avansada.repository.RepoProvedor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CURDProveedor {
	

	private final RepoProvedor repoProvedor;
	
	public Proveedor saveProveedor(Proveedor proveedor) {
		
		return repoProvedor.save(proveedor);
	}
	public Proveedor mdoficarProvedor(Proveedor proveedor) {
		return repoProvedor.save(proveedor);
	}
	
	public void eliminarProveedor(int id) {
		repoProvedor.deleteById(id);
	}
	public Proveedor buscar(int id) {
		return repoProvedor.buscarProveedor(id);
	}
	
	public Iterable<Proveedor>  listarprdocutos() {
		return repoProvedor.findAll();
	}

}
