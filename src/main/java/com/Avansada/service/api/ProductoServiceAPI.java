package com.Avansada.service.api;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.Avansada.Modelo.Producto;
@Service
public interface ProductoServiceAPI {
	
	Page<Producto> getAll(Pageable pageable);

}
