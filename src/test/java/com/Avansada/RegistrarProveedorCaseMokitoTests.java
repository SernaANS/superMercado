package com.Avansada;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Avansada.Modelo.Proveedor;
import com.Avansada.controller.ControllerProvedor;
import com.Avansada.repository.RepoProvedor;
import com.Avansada.service.CURDProveedor;

@ExtendWith(MockitoExtension.class)
class RegistrarProveedorCaseMokitoTests {

	@Mock
	  private RepoProvedor repotProveedor;

	  @InjectMocks
	  private CURDProveedor CRUDProveedor;


	@Test
	void savedUserHasRegistrationDate() {
		Proveedor proveedor = new Proveedor(1, "direcion", "luis", "320");		
		when(repotProveedor.save(any(Proveedor.class))).then(returnsFirstArg());
		Proveedor savedproveedor = CRUDProveedor.saveProveedor(proveedor);
		assertThat(savedproveedor.getNombre()).isNotNull();
	}
	@Test
	void modificar() {
		Proveedor proveedor = new Proveedor(2, "direcion2", "luis2", "320");		
		when(repotProveedor.save(any(Proveedor.class))).then(returnsFirstArg());
		Proveedor savedproveedor = CRUDProveedor.mdoficarProvedor(proveedor);
		assertThat(savedproveedor.getNombre()).isNotNull();
	}


}
