package com.Avansada.Modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the categoria database table.
 * 
 */
@Entity
@NamedQuery(name="Categoria.findAll", query="SELECT c FROM Categoria c")
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_categoria")
	private int idCategoria;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-one association to Subcategoria
	@OneToMany(mappedBy="categoria")
	private List<Subcategoria> subcategorias;

	public Categoria() {
	}

	public int getIdCategoria() {
		return this.idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Subcategoria> getSubcategorias() {
		return this.subcategorias;
	}

	public void setSubcategorias(List<Subcategoria> subcategorias) {
		this.subcategorias = subcategorias;
	}

	public Subcategoria addSubcategoria(Subcategoria subcategoria) {
		getSubcategorias().add(subcategoria);
		subcategoria.setCategoria(this);

		return subcategoria;
	}

	public Subcategoria removeSubcategoria(Subcategoria subcategoria) {
		getSubcategorias().remove(subcategoria);
		subcategoria.setCategoria(null);

		return subcategoria;
	}

}