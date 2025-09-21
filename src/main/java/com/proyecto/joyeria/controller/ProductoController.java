package com.proyecto.joyeria.controller;

import com.proyecto.joyeria.entity.*;
import com.proyecto.joyeria.service.ProductoService;
import com.proyecto.joyeria.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaRepository categoriaRepository;

    public ProductoController(ProductoService productoService, CategoriaRepository categoriaRepository) {
        this.productoService = productoService;
        this.categoriaRepository = categoriaRepository;
    }

    // Crear producto
    @PostMapping(consumes = "application/json")
    public Producto crearProducto(@RequestBody Producto producto) {
        Categoria categoria = null;
        if (producto.getCategoria() != null && producto.getCategoria().getId() != null) {
            categoria = categoriaRepository.findById(producto.getCategoria().getId()).orElse(null);
        }

        String urlImagen = producto.getImagen() != null ? producto.getImagen().getUrl() : null;

        return productoService.crearProducto(
                producto.getNombre(),
                producto.getCantidad(),
                producto.getPrecio(),
                categoria,
                urlImagen
        );
    }

    // Listar productos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public Producto obtenerProducto(@PathVariable Long id) {
        return productoService.obtenerProducto(id);
    }

    // Actualizar cantidad o precio
    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getId()).orElse(null);
        return productoService.actualizarProducto(
                id,
                producto.getNombre(),
                producto.getCantidad(),
                producto.getPrecio(),
                categoria,
                producto.getImagen().getUrl()
        );
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public Map<String, String> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return Map.of("message", "Producto eliminado correctamente");
    }


}
