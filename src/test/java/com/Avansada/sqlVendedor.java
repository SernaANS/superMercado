package com.Avansada;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.Avansada.Modelo.Vendedor;
import com.Avansada.repository.RepoProvedor;
import com.Avansada.repository.RepoVendedor;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class sqlVendedor {

	@Autowired
	  private TestEntityManager entityManager;
	@Autowired
	RepoVendedor repoVendedor;
	
	//saber si un usuario esta vacio
	@Test
	  public void should_find_no_users_if_repository_is_empty() {
	    Iterable<Vendedor> proveedorrez = repoVendedor.findAll();
	    
	    for (Vendedor Proveedor : proveedorrez) {
			System.out.println("Usuario:     "+Proveedor.toString());
		}

	    assertThat(proveedorrez).isEmpty();
	  }
	//guardar un usuario
	@Test
	  public void should_store_a_user() {
		Vendedor user = repoVendedor.save(new Vendedor(1, "apellido", "clave", "luis"));

	    assertThat(user).hasFieldOrPropertyWithValue("idVendedor", 1);
	    assertThat(user).hasFieldOrPropertyWithValue("apellido", "apellido");
	    assertThat(user).hasFieldOrPropertyWithValue("clave", "clave");
	    assertThat(user).hasFieldOrPropertyWithValue("nombre", "luis");
	  }

	//trae todos los usuario de la base datos
	  @Test
	  public void should_find_all_users() {
		  Vendedor usu1   = new Vendedor(1, "apellido", "clave", "luis");
	    entityManager.persist(usu1);

	    Vendedor usu2   = new Vendedor(2, "apellido2", "clave2", "luis2");
	    entityManager.persist(usu2);

	    Vendedor usu3  = new Vendedor(3, "apellid3", "clav3", "lui3");
	    entityManager.persist(usu3);

	    Iterable<Vendedor> users = repoVendedor.findAll();

	    assertThat(users).hasSize(3).contains(usu1, usu2  , usu3 );
	  }

	  //buscar usuario por una id
	  @Test
	  public void should_find_user_by_id() {
		  Vendedor usu1   = new Vendedor(1, "apellido", "clave", "luis");
	    entityManager.persist(usu1);

	    Vendedor usu2   = new Vendedor(2, "apellido2", "clave2", "luis2");
	    entityManager.persist(usu2);

	    Vendedor foundUser = repoVendedor.findById(usu2.getIdVendedor()).get();

	    assertThat(foundUser).isEqualTo(usu2);
	  }
/**
	  buscar un usuario por el nombre
	  
	  @Test
	  public void should_find_users_by_name_containing_string() {
		  Proveedor usu1   = new Proveedor(1, "direcion1", "luis1", "320");
	    entityManager.persist(usu1);

	    Proveedor usu2   = new Proveedor(2, "direcion2", "luis2", "320");
	    entityManager.persist(usu2);

	    Proveedor usu3  = new Proveedor(3, "direcion3", "luis3", "320");
	    entityManager.persist(usu3);

	    Iterable<Proveedor> users = (Iterable<Proveedor>) repotProveedor.findByName("luis1");

	    assertThat(users).hasSize(3).contains(usu1, usu3 );
	  }
**/
	  //actualizar usuario por llave primaria
	  @Test
	  public void should_update_user_by_id() {
		  Vendedor usu1   = new Vendedor(1, "apellido", "clave", "luis");
	    entityManager.persist(usu1);

	    Vendedor usu2   = new Vendedor(2, "apellido2", "clave2", "luis2");
	    entityManager.persist(usu2);

	    Vendedor updatedUsu = new Vendedor(3, "apellido2", "clave3", "luis3");

	    Vendedor usu = repoVendedor.findById(usu2.getIdVendedor()).get();
	    usu.setNombre(updatedUsu.getNombre());
	    usu.setDireccion(updatedUsu.getDireccion());
	    repoVendedor.save(usu);

	    Vendedor checkUsu = repoVendedor.findById(usu2.getIdVendedor()).get();
	    
	    assertThat(checkUsu.getIdVendedor()).isEqualTo(usu2.getIdVendedor());
	    assertThat(checkUsu.getNombre()).isEqualTo(updatedUsu.getNombre());
	    assertThat(checkUsu.getApellido()).isEqualTo(updatedUsu.getApellido());

	  }

	  //eliminar usuario por llave primaria
	  @Test
	  public void should_delete_user_by_id() {
		  Vendedor usu1   = new Vendedor(1, "apellido", "clave", "luis");
	    entityManager.persist(usu1);

	    Vendedor usu2   = new Vendedor(2, "apellido2", "clave2", "luis2");
	    entityManager.persist(usu2);

	    Vendedor usu3  = new Vendedor(3, "apellido3", "clave3", "luis3");
	    entityManager.persist(usu3);

	    repoVendedor.deleteById(usu2.getIdVendedor());

	    Iterable<Vendedor> users =repoVendedor.findAll();

	    assertThat(users).hasSize(2).contains(usu1, usu3 );
	  }

	  //eliminar todos los usuarios
	  @Test
	  public void should_delete_all_users() {
	    entityManager.persist(new Vendedor(1, "apellido", "clave", "luis"));
	    entityManager.persist(new Vendedor(2, "apellido2", "clave2", "luis2"));

	    repoVendedor.deleteAll();

	    assertThat(repoVendedor.findAll()).isEmpty();
	  }

}
