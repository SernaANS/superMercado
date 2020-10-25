package com.Avansada.Modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the factura database table.
 * 
 */
@Entity
@NamedQuery(name="Factura.findAll", query="SELECT f FROM Factura f")
public class Factura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_factura")
	private int idFactura;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Column(name="precio_total")
	private int precioTotal;

	//bi-directional many-to-one association to DetalleFactura
	@OneToMany(mappedBy="factura")
	private List<DetalleFactura> detalleFacturas;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	private Cliente cliente;

	//bi-directional many-to-one association to Vendedor
	@ManyToOne
	private Vendedor vendedor;

	public Factura() {
	}
	
	

	public Factura(int idFactura, Date fecha, int precioTotal, Cliente cliente, Vendedor vendedor) {
		super();
		this.idFactura = idFactura;
		this.fecha = fecha;
		this.precioTotal = precioTotal;
		this.cliente = cliente;
		this.vendedor = vendedor;
	}



	public int getIdFactura() {
		return this.idFactura;
	}

	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getPrecioTotal() {
		return this.precioTotal;
	}

	public void setPrecioTotal(int precioTotal) {
		this.precioTotal = precioTotal;
	}

	public List<DetalleFactura> getDetalleFacturas() {
		return this.detalleFacturas;
	}

	public void setDetalleFacturas(List<DetalleFactura> detalleFacturas) {
		this.detalleFacturas = detalleFacturas;
	}

	public DetalleFactura addDetalleFactura(DetalleFactura detalleFactura) {
		getDetalleFacturas().add(detalleFactura);
		detalleFactura.setFactura(this);

		return detalleFactura;
	}

	public DetalleFactura removeDetalleFactura(DetalleFactura detalleFactura) {
		getDetalleFacturas().remove(detalleFactura);
		detalleFactura.setFactura(null);

		return detalleFactura;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vendedor getVendedor() {
		return this.vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

}