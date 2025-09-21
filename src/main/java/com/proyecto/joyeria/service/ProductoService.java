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
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setCantidad(cantidad);
        producto.setPrecio(precio);
        producto.setCategoria(categoria);

        Imagen img = new Imagen();
        img.setUrl(urlImagen);
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
    public Producto actualizarProducto(Long id, String nombre, Integer cantidad, Double precio, Categoria categoria, String urlImagen) {
        Producto producto = obtenerProducto(id);

        if (nombre != null) producto.setNombre(nombre);
        if (cantidad != null) producto.setCantidad(cantidad);
        if (precio != null) producto.setPrecio(precio);
        if (categoria != null) producto.setCategoria(categoria);

        if (urlImagen != null) {
            Imagen img = producto.getImagen();
            if (img == null) {
                img = new Imagen();
            }
            img.setUrl(urlImagen);
            imagenRepository.save(img);
            producto.setImagen(img);
        }

        return productoRepository.save(producto);
    }
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
