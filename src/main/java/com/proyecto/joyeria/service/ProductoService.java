package com.proyecto.joyeria.service;

import com.proyecto.joyeria.entity.Categoria;
import com.proyecto.joyeria.entity.Imagen;
import com.proyecto.joyeria.entity.Producto;
import com.proyecto.joyeria.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Crear producto
    public Producto crearProducto(String nombre, Integer cantidad, Double precio, Categoria categoria, String urlImagen) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setCantidad(cantidad != null ? cantidad : 0);
        producto.setPrecio(precio != null ? precio : 0.0);
        producto.setCategoria(categoria);

        if (urlImagen != null && !urlImagen.isEmpty()) {
            Imagen imagen = new Imagen();
            imagen.setUrl(urlImagen);
            producto.setImagen(imagen);
        }

        return productoRepository.save(producto);
    }

    // Listar productos
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Obtener producto por ID
    public Producto obtenerProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    // Actualizar producto
    public Producto actualizarProducto(Long id, String nombre, Integer cantidad, Double precio, Categoria categoria, String urlImagen) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (nombre != null) producto.setNombre(nombre);
        if (cantidad != null) producto.setCantidad(cantidad);
        if (precio != null) producto.setPrecio(precio);
        if (categoria != null) producto.setCategoria(categoria);

        if (urlImagen != null && !urlImagen.isEmpty()) {
            if (producto.getImagen() == null) {
                producto.setImagen(new Imagen());
            }
            producto.getImagen().setUrl(urlImagen);
        }

        return productoRepository.save(producto);
    }

    // Eliminar producto
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }
}
