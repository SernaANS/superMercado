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
import com.Avansada.Modelo.Factura;
import com.Avansada.Modelo.Proveedor;
import com.Avansada.Modelo.Vendedor;
import com.Avansada.repository.RepoFactura;
import com.Avansada.repository.RepoProvedor;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class sqlFactura {

	@Autowired
	  private TestEntityManager entityManager;
	@Autowired
	RepoFactura repotFactura;
	//saber si un usuario esta vacio
		@Test
		  public void existeFactura() {
		    Iterable<Factura> factura = repotFactura.findAll();
		    
		    for (Factura Fact : factura) {
				System.out.println("Facturas:     "+Fact.toString());
			}

		    assertThat(factura).isEmpty();
		  }
		//guardar un usuario
		@Test
		  public void guardarFactura() {
			
			
			Cliente clin =new Cliente(1,"martinez","luis");
			Vendedor vend=new Vendedor(1,"carlos","pepe");
			entityManager.persist(clin);
			entityManager.persist(vend);
			Factura user = repotFactura.save(new Factura(1, 300, clin, vend));
			
		    assertThat(user).hasFieldOrPropertyWithValue("idFactura", 1);
		    assertThat(user).hasFieldOrPropertyWithValue("precioTotal", 300);
		    assertThat(user).hasFieldOrPropertyWithValue("cliente", clin);
		    assertThat(user).hasFieldOrPropertyWithValue("vendedor", vend);
		  }
		
		
		//trae todos los usuario de la base datos
		  @Test
		  public void listarFacturas() {

				Cliente clin =new Cliente(1,"martinez","luis");
				Vendedor vend=new Vendedor(1,"carlos","pepe");
				entityManager.persist(clin);
				entityManager.persist(vend);
			  Factura usu1   = new Factura(1, 300, clin, vend);
		    entityManager.persist(usu1);

		    Cliente clin2=new Cliente(2,"martinez2","luis2");
			Vendedor vend2=new Vendedor(2,"carlos2","pepe2");
			entityManager.persist(clin2);
			entityManager.persist(vend2);
		    Factura usu2   = new Factura(2, 300, clin2, vend2);
		    entityManager.persist(usu2);

		    Cliente clin3=new Cliente(3,"martinez3","luis3");
			Vendedor vend3=new Vendedor(3,"carlos3","pepe3");
		    Factura usu3  = new Factura(3, 300, clin3, vend3);
			entityManager.persist(clin3);
			entityManager.persist(vend3);
		    entityManager.persist(usu3);

		    Iterable<Factura> users = repotFactura.findAll();

		    assertThat(users).hasSize(3).contains(usu1, usu2  , usu3 );
		  }

		  //buscar usuario por una id
		  @Test
		  public void buscarFactura() {
			  Cliente clin =new Cliente(1,"martinez","luis");
				Vendedor vend=new Vendedor(1,"carlos","pepe");
				entityManager.persist(clin);
				entityManager.persist(vend);
			  Factura usu1   = new Factura(1, 300, clin, vend);
		    entityManager.persist(usu1);

		    Cliente clin2=new Cliente(2,"martinez2","luis2");
			Vendedor vend2=new Vendedor(2,"carlos2","pepe2");
			entityManager.persist(clin2);
			entityManager.persist(vend2);
		    Factura usu2   = new Factura(2, 300, clin2, vend2);
		    entityManager.persist(usu2);

		    Factura foundUser = repotFactura.findById(usu2.getIdFactura()).get();

		    assertThat(foundUser).isEqualTo(usu2);
		  }

		
		  //actualizar usuario por llave primaria
		  @Test
		  public void autualizarFactura() {
			  Cliente clin =new Cliente(1,"martinez","luis");
				Vendedor vend=new Vendedor(1,"carlos","pepe");
				entityManager.persist(clin);
				entityManager.persist(vend);
			  Factura usu1   = new Factura(1, 300, clin, vend);
		    entityManager.persist(usu1);

		    Cliente clin2=new Cliente(2,"martinez2","luis2");
			Vendedor vend2=new Vendedor(2,"carlos2","pepe2");
			entityManager.persist(clin2);
			entityManager.persist(vend2);
		    Factura usu2   = new Factura(2, 300, clin2, vend2);
		    entityManager.persist(usu2);

		    Cliente clin3=new Cliente(3,"martinez3","luis3");
			Vendedor vend3=new Vendedor(3,"carlos3","pepe3");
			entityManager.persist(clin3);
			entityManager.persist(vend3);
		    Factura updatedUsu  = new Factura(3, 300, clin3, vend3);

		    Factura usu = repotFactura.findById(usu2.getIdFactura()).get();
		    usu.setCliente(updatedUsu.getCliente());
		    usu.setVendedor(updatedUsu.getVendedor());
		    repotFactura.save(usu);

		    Factura checkUsu = repotFactura.findById(usu2.getIdFactura()).get();
		    
		    assertThat(checkUsu.getIdFactura()).isEqualTo(usu2.getIdFactura());
		    assertThat(checkUsu.getCliente()).isEqualTo(updatedUsu.getCliente());
		    assertThat(checkUsu.getVendedor()).isEqualTo(updatedUsu.getVendedor());

		  }
		  
		  //eliminar usuario por llave primaria
		  @Test
		  public void eliminarFactura() {
			  Cliente clin =new Cliente(1,"martinez","luis");
				Vendedor vend=new Vendedor(1,"carlos","pepe");
				entityManager.persist(clin);
				entityManager.persist(vend);
			  Factura usu1   = new Factura(1, 300, clin, vend);
		    entityManager.persist(usu1);

		    Cliente clin2=new Cliente(2,"martinez2","luis2");
			Vendedor vend2=new Vendedor(2,"carlos2","pepe2");
			entityManager.persist(clin2);
			entityManager.persist(vend2);
		    Factura usu2   = new Factura(2, 300, clin2, vend2);
		    entityManager.persist(usu2);

		    Cliente clin3=new Cliente(3,"martinez3","luis3");
			Vendedor vend3=new Vendedor(3,"carlos3","pepe3");
			entityManager.persist(clin3);
			entityManager.persist(vend3);
		    Factura usu3  = new Factura(3, 300, clin3, vend3);
		    entityManager.persist(usu3);

		    repotFactura.deleteById(usu2.getIdFactura());

		    Iterable<Factura> users = repotFactura.findAll();

		    assertThat(users).hasSize(2).contains(usu1, usu3 );
		  }
		  
		  //eliminar todos los usuarios
		  @Test
		  public void eliminarTodasFacturas() {
			
			Cliente clin =new Cliente(1,"martinez","luis");
			Vendedor vend=new Vendedor(1,"carlos","pepe");
			Cliente clin2=new Cliente(2,"martinez2","luis2");
			Vendedor vend2=new Vendedor(2,"carlos2","pepe2");
			
			entityManager.persist(clin2);
			entityManager.persist(vend2);
			entityManager.persist(clin);
			entityManager.persist(vend);
				
		    entityManager.persist(new Factura(1, 300, clin, vend));
		    entityManager.persist(new Factura(2, 300, clin2, vend2));

		    repotFactura.deleteAll();

		    assertThat(repotFactura.findAll()).isEmpty();
		  }

}
