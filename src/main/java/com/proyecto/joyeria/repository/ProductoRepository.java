package com.proyecto.joyeria.repository;

import com.proyecto.joyeria.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
