package com.Avansada;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.Avansada.Modelo.Proveedor;
import com.Avansada.repository.RepoProvedor;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class SqlTest {

	@Autowired
	  private TestEntityManager entityManager;
	@Autowired
	RepoProvedor repotProveedor;
	
	//saber si un usuario esta vacio
	@Test
	  public void should_find_no_users_if_repository_is_empty() {
	    Iterable<Proveedor> proveedorrez = repotProveedor.findAll();
	    
	    for (Proveedor Proveedor : proveedorrez) {
			System.out.println("Usuario:     "+Proveedor.toString());
		}

	    assertThat(proveedorrez).isEmpty();
	  }
	//guardar un usuario
	@Test
	  public void should_store_a_user() {
		Proveedor user = repotProveedor.save(new Proveedor(1, "direcion", "luis", "320"));

	    assertThat(user).hasFieldOrPropertyWithValue("idProveedor", 1);
	    assertThat(user).hasFieldOrPropertyWithValue("direccion", "direcion");
	    assertThat(user).hasFieldOrPropertyWithValue("nombre", "luis");
	    assertThat(user).hasFieldOrPropertyWithValue("telefono", "320");
	  }

	//trae todos los usuario de la base datos
	  @Test
	  public void should_find_all_users() {
		  Proveedor usu1   = new Proveedor(1, "direcion", "luis", "320");
	    entityManager.persist(usu1);

	    Proveedor usu2   = new Proveedor(2, "direcion2", "luis2", "320");
	    entityManager.persist(usu2);

	    Proveedor usu3  = new Proveedor(3, "direcion3", "luis3", "320");
	    entityManager.persist(usu3);

	    Iterable<Proveedor> users = repotProveedor.findAll();

	    assertThat(users).hasSize(3).contains(usu1, usu2  , usu3 );
	  }

	  //buscar usuario por una id
	  @Test
	  public void should_find_user_by_id() {
		  Proveedor usu1   = new Proveedor(1, "direcion1", "luis1", "320");
	    entityManager.persist(usu1);

	    Proveedor usu2   = new Proveedor(2, "direcion2", "luis2", "320");
	    entityManager.persist(usu2);

	    Proveedor foundUser = repotProveedor.findById(usu2.getIdProveedor()).get();

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
		  Proveedor usu1   = new Proveedor(1, "direcion1", "luis1", "320");
	    entityManager.persist(usu1);

	    Proveedor usu2   = new Proveedor(2, "direcion2", "luis2", "320");
	    entityManager.persist(usu2);

	    Proveedor updatedUsu = new Proveedor(3, "direcion3", "luis3", "320");

	    Proveedor usu = repotProveedor.findById(usu2.getIdProveedor()).get();
	    usu.setNombre(updatedUsu.getNombre());
	    usu.setDireccion(updatedUsu.getDireccion());
	    repotProveedor.save(usu);

	    Proveedor checkUsu = repotProveedor.findById(usu2.getIdProveedor()).get();
	    
	    assertThat(checkUsu.getIdProveedor()).isEqualTo(usu2.getIdProveedor());
	    assertThat(checkUsu.getNombre()).isEqualTo(updatedUsu.getNombre());
	    assertThat(checkUsu.getDireccion()).isEqualTo(updatedUsu.getDireccion());

	  }

	  //eliminar usuario por llave primaria
	  @Test
	  public void should_delete_user_by_id() {
		  Proveedor usu1   = new Proveedor(1, "direcion1", "luis1", "320");
	    entityManager.persist(usu1);

	    Proveedor usu2   = new Proveedor(2, "direcion2", "luis2", "320");
	    entityManager.persist(usu2);

	    Proveedor usu3  = new Proveedor(3, "direcion3", "luis3", "320");
	    entityManager.persist(usu3);

	    repotProveedor.deleteById(usu2.getIdProveedor());

	    Iterable<Proveedor> users = repotProveedor.findAll();

	    assertThat(users).hasSize(2).contains(usu1, usu3 );
	  }

	  //eliminar todos los usuarios
	  @Test
	  public void should_delete_all_users() {
	    entityManager.persist(new Proveedor(1, "direcion1", "luis1", "320"));
	    entityManager.persist(new Proveedor(2, "direcion2", "luis2", "320"));

	    repotProveedor.deleteAll();

	    assertThat(repotProveedor.findAll()).isEmpty();
	  }

}
