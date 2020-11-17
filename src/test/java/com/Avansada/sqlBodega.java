package com.Avansada;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.Avansada.Modelo.Bodega;
import com.Avansada.Modelo.Cliente;
import com.Avansada.repository.RepoBodega;
import com.Avansada.repository.RepoCliente;
@ExtendWith(SpringExtension.class)
@DataJpaTest
class sqlBodega {

	@Autowired
	  private TestEntityManager entityManager;
	@Autowired
	RepoBodega repotBodega;
	@Test
	  public void existeBodega() {
	    Iterable<Bodega> bodegas = repotBodega.findAll();
	    
	    for (Bodega bodeiga : bodegas) {
			System.out.println("bodeiga:     "+bodeiga.toString());
		}

	    assertThat(bodegas).isEmpty();
	  }
	//guardar un usuario
	@Test
	  public void guardarBodega() {
		Bodega user = repotBodega.save(new Bodega(1, 100, "papa"));

	    assertThat(user).hasFieldOrPropertyWithValue("idBodega", 1);
	    assertThat(user).hasFieldOrPropertyWithValue("espacioMaximo", 100);
	    assertThat(user).hasFieldOrPropertyWithValue("nombre", "papa");
	  }

	//trae todos los usuario de la base datos
	  @Test
	  public void listarUsuarios() {
		 Bodega user1 = repotBodega.save(new Bodega(1, 100, "papa"));
	    entityManager.persist(user1);

	    Bodega user2 = repotBodega.save(new Bodega(2, 100, "papa2"));
	    entityManager.persist(user2);

	    Bodega user3= repotBodega.save(new Bodega(3, 100, "papa3"));
	    entityManager.persist(user3);

	    Iterable<Bodega> users = repotBodega.findAll();

	    assertThat(users).hasSize(3).contains(user1, user2  ,user3);
	  }

	  //buscar usuario por una id
	  @Test
	  public void buscarBodega() {
			 Bodega user1 = repotBodega.save(new Bodega(1, 100, "papa"));
			    entityManager.persist(user1);

			    Bodega user2 = repotBodega.save(new Bodega(2, 100, "papa2"));
			    entityManager.persist(user2);
			    
	    Bodega foundUser = repotBodega.findById(user2.getIdBodega()).get();

	    assertThat(foundUser).isEqualTo(user2);
	  }

	  //actualizar usuario por llave primaria
	  @Test
	  public void actualizarBodega() {
			 Bodega user1 = repotBodega.save(new Bodega(1, 100, "papa"));
			    entityManager.persist(user1);

			    Bodega user2 = repotBodega.save(new Bodega(2, 100, "papa2"));
			    entityManager.persist(user2);


	    Bodega updatedUsu = new Bodega(3, 100, "papa3");

	    Bodega usu = repotBodega.findById(user2.getIdBodega()).get();
	    usu.setNombre(updatedUsu.getNombre());
	    usu.setEspacioMaximo(updatedUsu.getEspacioMaximo());
	    repotBodega.save(usu);

	    Bodega checkUsu = repotBodega.findById(user2.getIdBodega()).get();
	    
	    assertThat(checkUsu.getIdBodega()).isEqualTo(user2.getIdBodega());
	    assertThat(checkUsu.getNombre()).isEqualTo(updatedUsu.getNombre());
	    assertThat(checkUsu.getEspacioMaximo()).isEqualTo(updatedUsu.getEspacioMaximo());

	  }

	  //eliminar usuario por llave primaria
	  @Test
	  public void eliminarBodegaPorid() {
			 Bodega user1 = repotBodega.save(new Bodega(1, 100, "papa"));
			    entityManager.persist(user1);

			    Bodega user2 = repotBodega.save(new Bodega(2, 100, "papa2"));
			    entityManager.persist(user2);

			    Bodega user3= repotBodega.save(new Bodega(3, 100, "papa3"));
			    entityManager.persist(user3);
			    
		    repotBodega.deleteById(user2.getIdBodega());

	    Iterable<Bodega> users = repotBodega.findAll();

	    assertThat(users).hasSize(2).contains(user1, user3 );
	  }

	  //eliminar todos los usuarios
	  @Test
	  public void eliminarBodegas() {
	    entityManager.persist(new Bodega(1, 100, "papa"));
	    entityManager.persist(new Bodega(2, 100, "papa2"));

	    repotBodega.deleteAll();

	    assertThat(repotBodega.findAll()).isEmpty();
	  }

}
