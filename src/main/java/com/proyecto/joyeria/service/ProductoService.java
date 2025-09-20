package com.proyecto.joyeria.service;

import com.proyecto.joyeria.entity.*;
import com.proyecto.joyeria.repository.ProductoRepository;
import com.proyecto.joyeria.repository.ImagenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ImagenRepository imagenRepository;

    public ProductoService(ProductoRepository productoRepository, ImagenRepository imagenRepository) {
        this.productoRepository = productoRepository;
        this.imagenRepository = imagenRepository;
    }

    @Transactional
    public Producto crearProducto(String nombre, int cantidad, double precio, Categoria categoria, String urlImagen) {
        // Crear imagen
        Imagen img = new Imagen();
        img.setUrl(urlImagen);
        imagenRepository.save(img);

        // Crear producto
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setCantidad(cantidad);
        producto.setPrecio(precio);
        producto.setCategoria(categoria);
        producto.setImagen(img);

        return productoRepository.save(producto);
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto obtenerProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Transactional
    public Producto actualizarProducto(Long id, Integer cantidad, Double precio) {
        Producto producto = obtenerProducto(id);
        if (cantidad != null) producto.setCantidad(cantidad);
        if (precio != null) producto.setPrecio(precio);
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
