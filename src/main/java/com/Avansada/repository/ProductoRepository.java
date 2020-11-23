package com.Avansada.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Avansada.Modelo.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
