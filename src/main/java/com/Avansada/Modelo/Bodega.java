package com.Avansada.Modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the bodega database table.
 * 
 */
@Entity
@NamedQuery(name="Bodega.findAll", query="SELECT b FROM Bodega b")
public class Bodega implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_bodega")
	private int idBodega;

	private String direccion;

	@Column(name="espacio_maximo")
	private int espacioMaximo;

	private String nombre;

	//bi-directional many-to-one association to DetalleBodega
	@OneToMany(mappedBy="bodega")
	private List<DetalleBodega> detalleBodegas;

	public Bodega() {
	}
	
	public Bodega(int idBodega, int espacioMaximo, String nombre) {
		super();
		this.idBodega = idBodega;
		this.espacioMaximo = espacioMaximo;
		this.nombre = nombre;
	}



	public int getIdBodega() {
		return this.idBodega;
	}

	public void setIdBodega(int idBodega) {
		this.idBodega = idBodega;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getEspacioMaximo() {
		return this.espacioMaximo;
	}

	public void setEspacioMaximo(int espacioMaximo) {
		this.espacioMaximo = espacioMaximo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DetalleBodega> getDetalleBodegas() {
		return this.detalleBodegas;
	}

	public void setDetalleBodegas(List<DetalleBodega> detalleBodegas) {
		this.detalleBodegas = detalleBodegas;
	}

	public DetalleBodega addDetalleBodega(DetalleBodega detalleBodega) {
		getDetalleBodegas().add(detalleBodega);
		detalleBodega.setBodega(this);

		return detalleBodega;
	}

	public DetalleBodega removeDetalleBodega(DetalleBodega detalleBodega) {
		getDetalleBodegas().remove(detalleBodega);
		detalleBodega.setBodega(null);

		return detalleBodega;
	}

}