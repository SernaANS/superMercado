package com.Avansada.dto;

public class ProductoCliente {

	Integer id;
	String PRODUCTO;
	String NOMBREPRODUCTO;
	double PRECIO;
	String CANTIDAD;

	public ProductoCliente(Integer id, String pRODUCTO, String nOMBREPRODUCTO, double pRECIO, String cANTIDAD) {
		super();
		this.id = id;
		PRODUCTO = pRODUCTO;
		NOMBREPRODUCTO = nOMBREPRODUCTO;
		PRECIO = pRECIO;
		CANTIDAD = cANTIDAD;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPRODUCTO() {
		return PRODUCTO;
	}

	public void setPRODUCTO(String pRODUCTO) {
		PRODUCTO = pRODUCTO;
	}

	public String getNOMBREPRODUCTO() {
		return NOMBREPRODUCTO;
	}

	public void setNOMBREPRODUCTO(String nOMBREPRODUCTO) {
		NOMBREPRODUCTO = nOMBREPRODUCTO;
	}

	public double getPRECIO() {
		return PRECIO;
	}

	public void setPRECIO(double pRECIO) {
		PRECIO = pRECIO;
	}

	public String getCANTIDAD() {
		return CANTIDAD;
	}

	public void setCANTIDAD(String cANTIDAD) {
		CANTIDAD = cANTIDAD;
	}

}
