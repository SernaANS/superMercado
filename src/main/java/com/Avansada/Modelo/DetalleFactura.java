package com.Avansada.Modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the detalle_factura database table.
 * 
 */
@Entity
@Table(name="detalle_factura")
@NamedQuery(name="DetalleFactura.findAll", query="SELECT d FROM DetalleFactura d")
public class DetalleFactura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_factura")
	private int idFactura;

	private int cantidad;

	//bi-directional many-to-one association to Despacho
	@OneToMany(mappedBy="detalleFactura")
	private List<Despacho> despachos;

	//bi-directional many-to-one association to Factura
	@ManyToOne
	private Factura factura;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	private Producto producto;

	public DetalleFactura() {
	}

	public int getIdFactura() {
		return this.idFactura;
	}

	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}

	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public List<Despacho> getDespachos() {
		return this.despachos;
	}

	public void setDespachos(List<Despacho> despachos) {
		this.despachos = despachos;
	}

	public Despacho addDespacho(Despacho despacho) {
		getDespachos().add(despacho);
		despacho.setDetalleFactura(this);

		return despacho;
	}

	public Despacho removeDespacho(Despacho despacho) {
		getDespachos().remove(despacho);
		despacho.setDetalleFactura(null);

		return despacho;
	}

	public Factura getFactura() {
		return this.factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}