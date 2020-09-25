package com.Avansada.Modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the despacho database table.
 * 
 */
@Entity
@NamedQuery(name="Despacho.findAll", query="SELECT d FROM Despacho d")
public class Despacho implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_detalle_pedido")
	private int idDetallePedido;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	//bi-directional many-to-one association to DetalleFactura
	@ManyToOne
	@JoinColumn(name="detalle_factura_id_factura")
	private DetalleFactura detalleFactura;

	public Despacho() {
	}

	public int getIdDetallePedido() {
		return this.idDetallePedido;
	}

	public void setIdDetallePedido(int idDetallePedido) {
		this.idDetallePedido = idDetallePedido;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public DetalleFactura getDetalleFactura() {
		return this.detalleFactura;
	}

	public void setDetalleFactura(DetalleFactura detalleFactura) {
		this.detalleFactura = detalleFactura;
	}

}