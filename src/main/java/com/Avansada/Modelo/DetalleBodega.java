package com.Avansada.Modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the detalle_bodega database table.
 * 
 */
@Entity
@Table(name="detalle_bodega")
@NamedQuery(name="DetalleBodega.findAll", query="SELECT d FROM DetalleBodega d")
public class DetalleBodega implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_detalle_bodega")
	private int idDetalleBodega;

	@Column(name="cantidad_minima")
	private int cantidadMinima;

	@Column(name="cantidad_producto")
	private int cantidadProducto;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	//bi-directional many-to-one association to Bodega
	@ManyToOne
	private Bodega bodega;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	private Producto producto;

	public DetalleBodega() {
	}

	public int getIdDetalleBodega() {
		return this.idDetalleBodega;
	}

	public void setIdDetalleBodega(int idDetalleBodega) {
		this.idDetalleBodega = idDetalleBodega;
	}

	public int getCantidadMinima() {
		return this.cantidadMinima;
	}

	public void setCantidadMinima(int cantidadMinima) {
		this.cantidadMinima = cantidadMinima;
	}

	public int getCantidadProducto() {
		return this.cantidadProducto;
	}

	public void setCantidadProducto(int cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Bodega getBodega() {
		return this.bodega;
	}

	public void setBodega(Bodega bodega) {
		this.bodega = bodega;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}