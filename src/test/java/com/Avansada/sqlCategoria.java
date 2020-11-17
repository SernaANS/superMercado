package com.Avansada;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.Avansada.Modelo.Categoria;
import com.Avansada.Modelo.Cliente;
import com.Avansada.repository.RepoCategoria;
import com.Avansada.repository.RepoCliente;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class sqlCategoria {

	@Autowired
	  private TestEntityManager entityManager;
	@Autowired
	RepoCategoria repoCategoria;
	
	@Test
	  public void existeCategoria() {
	    Iterable<Categoria> categorias = repoCategoria.findAll();
	    
	    for (Categoria categoria : categorias) {
			System.out.println("cliente:     "+categoria.toString());
		}

	    assertThat(categorias).isEmpty();
	  }
	//guardar un usuario
	@Test
	  public void guardarCategoria() {
		Categoria user = repoCategoria.save(new Categoria(1, "des1", "cat1"));

	    assertThat(user).hasFieldOrPropertyWithValue("idCategoria", 1);
	    assertThat(user).hasFieldOrPropertyWithValue("descripcion", "des1");
	    assertThat(user).hasFieldOrPropertyWithValue("nombre", "cat1");
	  }

	//trae todos los usuario de la base datos
	  @Test
	  public void listarCategorias() {
		  Categoria user1 = repoCategoria.save(new Categoria(1, "des1", "cat1"));
	    entityManager.persist(user1);

	    Categoria user2 = repoCategoria.save(new Categoria(2, "des2", "cat2"));
	    entityManager.persist(user2);

	    Categoria user3= repoCategoria.save(new Categoria(3, "des3", "cat3"));
	    entityManager.persist(user3);

	    Iterable<Categoria> users = repoCategoria.findAll();

	    assertThat(users).hasSize(3).contains(user1, user2  ,user3);
	  }

	  //buscar usuario por una id
	  @Test
	  public void buscarCategoria() {
		  Categoria user1 = repoCategoria.save(new Categoria(1, "des1", "cat1"));
		    entityManager.persist(user1);

		    Categoria user2 = repoCategoria.save(new Categoria(2, "des2", "cat2"));
		    entityManager.persist(user2);


		    Categoria foundUser = repoCategoria.findById(user2.getIdCategoria()).get();

	    assertThat(foundUser).isEqualTo(user2);
	  }

	  //actualizar usuario por llave primaria
	  @Test
	  public void actualizarCategoria() {
		  Categoria user1 = repoCategoria.save(new Categoria(1, "des1", "cat1"));
		    entityManager.persist(user1);

		    Categoria user2 = repoCategoria.save(new Categoria(2, "des2", "cat2"));
		    entityManager.persist(user2);


		    Categoria updatedUsu = new Categoria(3,"des2", "cat2");
		
	    Categoria usu = repoCategoria.findById(user2.getIdCategoria()).get();
	    usu.setNombre(updatedUsu.getNombre());
	    usu.setDescripcion(updatedUsu.getDescripcion());
	    repoCategoria.save(usu);

	    Categoria checkUsu = repoCategoria.findById(user2.getIdCategoria()).get();
	    
	    assertThat(checkUsu.getIdCategoria()).isEqualTo(user2.getIdCategoria());
	    assertThat(checkUsu.getNombre()).isEqualTo(updatedUsu.getNombre());
	    assertThat(checkUsu.getDescripcion()).isEqualTo(updatedUsu.getDescripcion());

	  }

	  //eliminar usuario por llave primaria
	  @Test
	  public void eliminarCategoriaPorid() {
		  Categoria user1 = repoCategoria.save(new Categoria(1, "des1", "cat1"));
		    entityManager.persist(user1);

		    Categoria user2 = repoCategoria.save(new Categoria(2, "des2", "cat2"));
		    entityManager.persist(user2);

		    Categoria user3= repoCategoria.save(new Categoria(3, "des3", "cat3"));
		    entityManager.persist(user3);
		    
		    repoCategoria.deleteById(user2.getIdCategoria());

	    Iterable<Categoria> users = repoCategoria.findAll();

	    assertThat(users).hasSize(2).contains(user1, user3 );
	  }

	  //eliminar todos los usuarios
	  @Test
	  public void eliminarCategoria() {
	    entityManager.persist(new Categoria(1, "des1", "cat1"));
	    entityManager.persist(new Categoria(2, "des2", "cat2"));

	    repoCategoria.deleteAll();

	    assertThat(repoCategoria.findAll()).isEmpty();
	  }


}
