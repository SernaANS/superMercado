package com.Avansada.service.implement;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Avansada.Modelo.Producto;
import com.Avansada.repository.ProductoRepository;
import com.Avansada.service.api.ProductoServiceAPI;

@Service
public class ProductoServiceImpl implements ProductoServiceAPI {

	@Autowired
	private ProductoRepository productoRepository;



	@Override
	public Page<Producto> getAll(Pageable pageable) {

		return productoRepository.findAll(pageable);
	}

}



























