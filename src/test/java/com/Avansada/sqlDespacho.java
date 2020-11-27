package com.Avansada;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.Avansada.Modelo.Despacho;
import com.Avansada.Modelo.DetalleFactura;
import com.Avansada.Modelo.Vendedor;
import com.Avansada.repository.RepoDespacho;
import com.Avansada.repository.RepoVendedor;
@ExtendWith(SpringExtension.class)
@DataJpaTest
class sqlDespacho {

	@Autowired
	  private TestEntityManager entityManager;
	@Autowired
	RepoDespacho repoVDespacho;
	//saber si un usuario esta vacio
		@Test
		  public void should_find_no_users_if_repository_is_empty() {
		    Iterable<Despacho> proveedorrez = repoVDespacho.findAll();
		    
		    for (Despacho Proveedor : proveedorrez) {
				System.out.println("Usuario:     "+Proveedor.toString());
			}

		    assertThat(proveedorrez).isEmpty();
		  }
		//guardar un usuario
		@Test
		  public void should_store_a_user() {
			DetalleFactura destalleF= new DetalleFactura(1,30);
			Date fechaa= new Date(20202,3,3);
			Despacho user = repoVDespacho.save(new Despacho(1,fechaa));

			assertThat(user).hasFieldOrPropertyWithValue("idDetallePedido", 1);
		    assertThat(user).hasFieldOrPropertyWithValue("fecha", fechaa);
		  }

		//trae todos los usuario de la base datos
		  @Test
		  public void should_find_all_users() {
			
			DetalleFactura destalleF= new DetalleFactura(1,30);
			Date fechaa= new Date(20202,3,3);
			Despacho usu1 = repoVDespacho.save(new Despacho(1,fechaa));
		    entityManager.persist(usu1);

			DetalleFactura destalleF2= new DetalleFactura(1,30);
			Date fechaa2= new Date(20202,3,3);
			Despacho usu2 = repoVDespacho.save(new Despacho(2,fechaa2));
		    entityManager.persist(usu2);

		    DetalleFactura destalleF3= new DetalleFactura(1,30);
			Date fechaa3= new Date(20202,3,3);
			Despacho usu3 = repoVDespacho.save(new Despacho(3,fechaa2));
		    entityManager.persist(usu3);

		    Iterable<Despacho> users = repoVDespacho.findAll();

		    assertThat(users).hasSize(3).contains(usu1, usu2  , usu3 );
		  }

		  //buscar usuario por una id
		  @Test
		  public void should_find_user_by_id() {
			  DetalleFactura destalleF= new DetalleFactura(1,30);
				Date fechaa= new Date(20202,3,3);
				Despacho usu1 = repoVDespacho.save(new Despacho(1,fechaa));
			    entityManager.persist(usu1);

				DetalleFactura destalleF2= new DetalleFactura(1,30);
				Date fechaa2= new Date(20202,3,3);
				Despacho usu2 = repoVDespacho.save(new Despacho(2,fechaa2));
			    entityManager.persist(usu2);

		    Despacho foundUser = repoVDespacho.findById(usu2.getIdDetallePedido()).get();

		    assertThat(foundUser).isEqualTo(usu2);
		  }

		  //actualizar usuario por llave primaria
		  @Test
		  public void should_update_user_by_id() {
			  DetalleFactura destalleF= new DetalleFactura(1,30);
				Date fechaa= new Date(20202,3,3);
				Despacho usu1 = repoVDespacho.save(new Despacho(1,fechaa));
			    entityManager.persist(usu1);

				DetalleFactura destalleF2= new DetalleFactura(1,30);
				Date fechaa2= new Date(20202,3,3);
				Despacho usu2 = repoVDespacho.save(new Despacho(2,fechaa2));
			    entityManager.persist(usu2);

			    DetalleFactura destalleF3= new DetalleFactura(1,30);
				Date fechaa3= new Date(20202,3,3);
				Despacho usu3 = repoVDespacho.save(new Despacho(3,fechaa2));

		    Despacho usu = repoVDespacho.findById(usu2.getIdDetallePedido()).get();
		    usu.setFecha(usu3.getFecha());
		    usu.setDetalleFactura(usu3.getDetalleFactura());
		    repoVDespacho.save(usu);

		    Despacho checkUsu = repoVDespacho.findById(usu2.getIdDetallePedido()).get();
		    
		    assertThat(checkUsu.getFecha()).isEqualTo(usu2.getFecha());
		  }

		  //eliminar usuario por llave primaria
		  @Test
		  public void should_delete_user_by_id() {
			  DetalleFactura destalleF= new DetalleFactura(1,30);
				Date fechaa= new Date(20202,3,3);
				Despacho usu1 = repoVDespacho.save(new Despacho(1,fechaa));
			    entityManager.persist(usu1);

				DetalleFactura destalleF2= new DetalleFactura(1,30);
				Date fechaa2= new Date(20202,3,3);
				Despacho usu2 = repoVDespacho.save(new Despacho(2,fechaa2));
			    entityManager.persist(usu2);

			    DetalleFactura destalleF3= new DetalleFactura(1,30);
				Date fechaa3= new Date(20202,3,3);
				Despacho usu3 = repoVDespacho.save(new Despacho(3,fechaa2));
			    entityManager.persist(usu3);

		    repoVDespacho.deleteById(usu2.getIdDetallePedido());

		    Iterable<Despacho> users =repoVDespacho.findAll();

		    assertThat(users).hasSize(2).contains(usu1, usu3 );
		  }

		  //eliminar todos los usuarios
		  @Test
		  public void should_delete_all_users() {
			DetalleFactura destalleF1= new DetalleFactura(1,30);
			Date fechaa1= new Date(20202,3,3);
			

			DetalleFactura destalleF2= new DetalleFactura(1,30);
			Date fechaa2= new Date(20202,3,3);
		    entityManager.persist(new Despacho(1, fechaa1));
		    entityManager.persist(new Despacho(2, fechaa2));

		    repoVDespacho.deleteAll();

		    assertThat(repoVDespacho.findAll()).isEmpty();
		  }

}
