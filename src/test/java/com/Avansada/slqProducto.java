package com.Avansada;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.Avansada.Modelo.Cliente;
import com.Avansada.Modelo.Producto;
import com.Avansada.repository.RepoCliente;
import com.Avansada.repository.RepoProducto;
@ExtendWith(SpringExtension.class)
@DataJpaTest
class slqProducto {
	@Autowired
	  private TestEntityManager entityManager;
	@Autowired
	RepoProducto repotProducto;
	
	@Test
	  public void existeProducto() {
	    Iterable<Producto> producots = repotProducto.findAll();
	    
	    for (Producto producto : producots) {
			System.out.println("cliente:     "+producto.toString());
		}

	    assertThat(producots).isEmpty();
	  }
	//guardar un usuario
	@Test
	  public void guardarProducto() {
		Producto user = repotProducto.save(new Producto(1, "descripcion1", "papa1"));

	    assertThat(user).hasFieldOrPropertyWithValue("idProducto", 1);
	    assertThat(user).hasFieldOrPropertyWithValue("decripcion", "descripcion1");
	    assertThat(user).hasFieldOrPropertyWithValue("nombre", "papa1");
	  }

	//trae todos los usuario de la base datos
	  @Test
	  public void listarProducto() {
		  Producto user1 = repotProducto.save(new Producto(1, "descripcion1", "papa1"));
	    entityManager.persist(user1);

	    Producto user2 = repotProducto.save(new Producto(2, "descripcion2", "papa2"));
	    entityManager.persist(user2);

	    Producto user3= repotProducto.save(new Producto(3, "descripcion3", "papa3"));
	    entityManager.persist(user3);

	    Iterable<Producto> users = repotProducto.findAll();

	    assertThat(users).hasSize(3).contains(user1, user2  ,user3);
	  }

	  //buscar usuario por una id
	  @Test
	  public void buscarProducto(){
		  Producto user1 = repotProducto.save(new Producto(1, "descripcion1", "papa1"));
		    entityManager.persist(user1);

		    Producto user2 = repotProducto.save(new Producto(2, "descripcion2", "papa2"));
		    entityManager.persist(user2);


	    Producto foundUser = repotProducto.findById(user2.getIdProducto()).get();

	    assertThat(foundUser).isEqualTo(user2);
	  }

	  //actualizar usuario por llave primaria
	  @Test
	  public void actualizarProducto() {
		  Producto user1 = repotProducto.save(new Producto(1, "descripcion1", "papa1"));
		    entityManager.persist(user1);

		    Producto user2 = repotProducto.save(new Producto(2, "descripcion2", "papa2"));
		    entityManager.persist(user2);

		    Producto updatedUsu = new Producto(3, "descripcion3", "papa3");

		    Producto usu = repotProducto.findById(user2.getIdProducto()).get();
	    usu.setNombre(updatedUsu.getNombre());
	    usu.setDecripcion(updatedUsu.getDecripcion());
	    repotProducto.save(usu);

	    Producto checkUsu = repotProducto.findById(user2.getIdProducto()).get();
	    
	    assertThat(checkUsu.getIdProducto()).isEqualTo(user2.getIdProducto());
	    assertThat(checkUsu.getNombre()).isEqualTo(updatedUsu.getNombre());
	    assertThat(checkUsu.getDecripcion()).isEqualTo(updatedUsu.getDecripcion());

	  }

	  //eliminar usuario por llave primaria
	  @Test
	  public void eliminarProductoPorid() {
		  Producto user1 = repotProducto.save(new Producto(1, "descripcion1", "papa1"));
		    entityManager.persist(user1);

		    Producto user2 = repotProducto.save(new Producto(2, "descripcion2", "papa2"));
		    entityManager.persist(user2);

		    Producto user3= repotProducto.save(new Producto(3, "descripcion3", "papa3"));
		    entityManager.persist(user3);

		    repotProducto.deleteById(user2.getIdProducto());

	    Iterable<Producto> users = repotProducto.findAll();

	    assertThat(users).hasSize(2).contains(user1, user3 );
	  }

	  //eliminar todos los usuarios
	  @Test
	  public void eliminarProducto() {
	    entityManager.persist(new Producto(1, "descripcion1", "papa1"));
	    entityManager.persist(new Producto(2, "descripcion2", "papa2"));

	    repotProducto.deleteAll();

	    assertThat(repotProducto.findAll()).isEmpty();
	  }
}
