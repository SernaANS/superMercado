package com.Avansada.Modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the admin database table.
 * 
 */
@Entity
@NamedQuery(name="Admin.findAll", query="SELECT a FROM Admin a")
public class Admin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_admin")
	private String idAdmin;

	private String clave;

	private String correo;

	private String nombre;

	public Admin() {
	}
	

	public Admin(String idAdmin, String clave, String correo, String nombre) {
		super();
		this.idAdmin = idAdmin;
		this.clave = clave;
		this.correo = correo;
		this.nombre = nombre;
	}


	public String getIdAdmin() {
		return this.idAdmin;
	}

	public void setIdAdmin(String idAdmin) {
		this.idAdmin = idAdmin;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}