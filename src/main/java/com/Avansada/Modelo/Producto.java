package com.Avansada.Modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the producto database table.
 * 
 */
@Entity
@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_producto")
	private int idProducto;

	
	private String decripcion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_vencimiento")
	private Date fechaVencimiento;

	private String foto;

	private String nombre;

	@Column(name="precio_compra_unidad")
	private int precioCompraUnidad;

	@Column(name="precio_venta_unidad")
	private int precioVentaUnidad;

	//bi-directional many-to-one association to DetalleBodega
	@OneToMany(mappedBy="producto")
	private List<DetalleBodega> detalleBodegas;

	//bi-directional many-to-one association to DetalleFactura
	@OneToMany(mappedBy="producto")
	private List<DetalleFactura> detalleFacturas;

	//bi-directional many-to-one association to Subcategoria
	@ManyToOne
	private Subcategoria subcategoria;

	//bi-directional many-to-one association to Proveedor
	@ManyToOne
	private Proveedor proveedor;

	public Producto() {
	}
	
	

	public Producto(int idProducto, String decripcion, String nombre) {
		super();
		this.idProducto = idProducto;
		this.decripcion = decripcion;
		this.nombre = nombre;
	}



	public int getIdProducto() {
		return this.idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getDecripcion() {
		return this.decripcion;
	}

	public void setDecripcion(String decripcion) {
		this.decripcion = decripcion;
	}

	public Date getFechaVencimiento() {
		return this.fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPrecioCompraUnidad() {
		return this.precioCompraUnidad;
	}

	public void setPrecioCompraUnidad(int precioCompraUnidad) {
		this.precioCompraUnidad = precioCompraUnidad;
	}

	public int getPrecioVentaUnidad() {
		return this.precioVentaUnidad;
	}

	public void setPrecioVentaUnidad(int precioVentaUnidad) {
		this.precioVentaUnidad = precioVentaUnidad;
	}

	public List<DetalleBodega> getDetalleBodegas() {
		return this.detalleBodegas;
	}

	public void setDetalleBodegas(List<DetalleBodega> detalleBodegas) {
		this.detalleBodegas = detalleBodegas;
	}

	public DetalleBodega addDetalleBodega(DetalleBodega detalleBodega) {
		getDetalleBodegas().add(detalleBodega);
		detalleBodega.setProducto(this);

		return detalleBodega;
	}

	public DetalleBodega removeDetalleBodega(DetalleBodega detalleBodega) {
		getDetalleBodegas().remove(detalleBodega);
		detalleBodega.setProducto(null);

		return detalleBodega;
	}

	public List<DetalleFactura> getDetalleFacturas() {
		return this.detalleFacturas;
	}

	public void setDetalleFacturas(List<DetalleFactura> detalleFacturas) {
		this.detalleFacturas = detalleFacturas;
	}

	public DetalleFactura addDetalleFactura(DetalleFactura detalleFactura) {
		getDetalleFacturas().add(detalleFactura);
		detalleFactura.setProducto(this);

		return detalleFactura;
	}

	public DetalleFactura removeDetalleFactura(DetalleFactura detalleFactura) {
		getDetalleFacturas().remove(detalleFactura);
		detalleFactura.setProducto(null);

		return detalleFactura;
	}

	public Subcategoria getSubcategoria() {
		return this.subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}



	@Override
	public String toString() {
		return "Producto [idProducto=" + idProducto + ", decripcion=" + decripcion + ", fechaVencimiento="
				+ fechaVencimiento + ", foto=" + foto + ", nombre=" + nombre + ", precioCompraUnidad="
				+ precioCompraUnidad + ", precioVentaUnidad=" + precioVentaUnidad + ", detalleBodegas=" + detalleBodegas
				+ ", detalleFacturas=" + detalleFacturas + ", subcategoria=" + subcategoria + ", proveedor=" + proveedor
				+ "]";
	}
	
	

}