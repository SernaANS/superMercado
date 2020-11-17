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
import com.Avansada.Modelo.Proveedor;
import com.Avansada.repository.RepoCliente;
import com.Avansada.repository.RepoProvedor;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class sqlCliente {

	@Autowired
	  private TestEntityManager entityManager;
	@Autowired
	RepoCliente repotCliente;
	
	@Test
	  public void existeCliente() {
	    Iterable<Cliente> clientes = repotCliente.findAll();
	    
	    for (Cliente cliente : clientes) {
			System.out.println("cliente:     "+cliente.toString());
		}

	    assertThat(clientes).isEmpty();
	  }
	//guardar un usuario
	@Test
	  public void guardarCliente() {
		Cliente user = repotCliente.save(new Cliente(1, "martinez", "luis"));

	    assertThat(user).hasFieldOrPropertyWithValue("idCliente", 1);
	    assertThat(user).hasFieldOrPropertyWithValue("apellido", "martinez");
	    assertThat(user).hasFieldOrPropertyWithValue("nombre", "luis");
	  }

	//trae todos los usuario de la base datos
	  @Test
	  public void listarUsuarios() {
		  Cliente user1 = repotCliente.save(new Cliente(1, "martinez", "luis"));
	    entityManager.persist(user1);

	    Cliente user2 = repotCliente.save(new Cliente(2, "martinez2", "luis2"));
	    entityManager.persist(user2);

	    Cliente user3= repotCliente.save(new Cliente(3, "martinez3", "luis3"));
	    entityManager.persist(user3);

	    Iterable<Cliente> users = repotCliente.findAll();

	    assertThat(users).hasSize(3).contains(user1, user2  ,user3);
	  }

	  //buscar usuario por una id
	  @Test
	  public void buscarCliente() {
		  Cliente user1 = repotCliente.save(new Cliente(1, "martinez", "luis"));
		    entityManager.persist(user1);

		    Cliente user2 = repotCliente.save(new Cliente(2, "martinez2", "luis2"));
		    entityManager.persist(user2);


	    Cliente foundUser = repotCliente.findById(user2.getIdCliente()).get();

	    assertThat(foundUser).isEqualTo(user2);
	  }

	  //actualizar usuario por llave primaria
	  @Test
	  public void actualizarCliente() {
		  Cliente user1 = repotCliente.save(new Cliente(1, "martinez", "luis"));
		    entityManager.persist(user1);

		    Cliente user2 = repotCliente.save(new Cliente(2, "martinez2", "luis2"));
		    entityManager.persist(user2);


	    Cliente updatedUsu = new Cliente(3, "martinez3", "luis3");

	    Cliente usu = repotCliente.findById(user2.getIdCliente()).get();
	    usu.setNombre(updatedUsu.getNombre());
	    usu.setApellido(updatedUsu.getApellido());
	    repotCliente.save(usu);

	    Cliente checkUsu = repotCliente.findById(user2.getIdCliente()).get();
	    
	    assertThat(checkUsu.getIdCliente()).isEqualTo(user2.getIdCliente());
	    assertThat(checkUsu.getNombre()).isEqualTo(updatedUsu.getNombre());
	    assertThat(checkUsu.getApellido()).isEqualTo(updatedUsu.getApellido());

	  }

	  //eliminar usuario por llave primaria
	  @Test
	  public void should_delete_user_by_id() {
		  Cliente user1 = repotCliente.save(new Cliente(1, "martinez", "luis"));
		    entityManager.persist(user1);

		    Cliente user2 = repotCliente.save(new Cliente(2, "martinez2", "luis2"));
		    entityManager.persist(user2);

		    Cliente user3= repotCliente.save(new Cliente(3, "martinez3", "luis3"));
		    entityManager.persist(user3);

	    repotCliente.deleteById(user2.getIdCliente());

	    Iterable<Cliente> users = repotCliente.findAll();

	    assertThat(users).hasSize(2).contains(user1, user3 );
	  }

	  //eliminar todos los usuarios
	  @Test
	  public void eliminarCliente() {
	    entityManager.persist(new Cliente(1, "martinez", "luis"));
	    entityManager.persist(new Cliente(2, "martinez2", "luis2"));

	    repotCliente.deleteAll();

	    assertThat(repotCliente.findAll()).isEmpty();
	  }

}
