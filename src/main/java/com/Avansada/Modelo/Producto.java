package com.Avansada.Modelo;

import java.io.Serializable;
import javax.persistence.*;
<<<<<<< HEAD
=======
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
>>>>>>> a79c53e... En este se creo la interfas de producto y la funcionalidad del crud

import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;

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

	//@NotNull(message="{date-valid}")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name="fecha_vencimiento")
    private Date fechaVencimiento;
	
<<<<<<< HEAD
	private String nombre;

	@Column(name="precio_compra_unidad")
	private int precioCompraUnidad;

	@Column(name="precio_venta_unidad")
=======
    @Column(unique = true)
    //@NotEmpty(message = "{error.campoObligatorio}")
	//@Pattern(regexp = "[A-Za-z ]+", message = "{error.sololetras}")
	private String nombre;

    
	@Column(name="precio_compra_unidad")

	//@NotNull(message = "{error.campoObligatorio}")
	//@Min(value = 1, message = "El precio mínimo es 1")
	private int precioCompraUnidad;

	@Column(name="precio_venta_unidad")

	//@NotNull(message = "{error.campoObligatorio}")
	//@Min(value = 1, message = "El precio mínimo es 1")
>>>>>>> a79c53e... En este se creo la interfas de producto y la funcionalidad del crud
	private int precioVentaUnidad;

	//bi-directional many-to-one association to DetalleBodega
	@OneToMany(mappedBy="producto")
	private List<DetalleBodega> detalleBodegas;

	//bi-directional many-to-one association to DetalleFactura
	@OneToMany(mappedBy="producto")
	private List<DetalleFactura> detalleFacturas;

	//bi-directional many-to-one association to Proveedor
	@ManyToOne
	private Proveedor proveedor;

	//bi-directional many-to-one association to Subcategoria
	@ManyToOne
	private Subcategoria subcategoria;

	public Producto() {
	}
<<<<<<< HEAD
=======
	
	/**

	public Producto(int idProducto, String decripcion, Date fechaVencimiento,@NotEmpty(message = "hola lindo, se te olvido  nombre") @Pattern(regexp = "[A-Za-z ]+", message = "solo se permite letras") String nombre, int precioCompraUnidad,
			int precioVentaUnidad, List<DetalleBodega> detalleBodegas, List<DetalleFactura> detalleFacturas,
			Proveedor proveedor, Subcategoria subcategoria) {
		super();
		this.idProducto = idProducto;
		this.decripcion = decripcion;
		this.fechaVencimiento = fechaVencimiento;
		this.nombre = nombre;
		this.precioCompraUnidad = precioCompraUnidad;
		this.precioVentaUnidad = precioVentaUnidad;
		this.detalleBodegas = detalleBodegas;
		this.detalleFacturas = detalleFacturas;
		this.proveedor = proveedor;
		this.subcategoria = subcategoria;
	}

**/
>>>>>>> a79c53e... En este se creo la interfas de producto y la funcionalidad del crud

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

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Subcategoria getSubcategoria() {
		return this.subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

}