package com.proyecto.joyeria.controller;

import com.proyecto.joyeria.entity.*;
import com.proyecto.joyeria.service.ProductoService;
import com.proyecto.joyeria.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping
    public Producto crearProducto(
            @RequestParam String nombre,
            @RequestParam int cantidad,
            @RequestParam double precio,
            @RequestParam Long idCategoria,
            @RequestParam String urlImagen) {

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        return productoService.crearProducto(nombre, cantidad, precio, categoria, urlImagen);
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
    public Producto actualizarProducto(
            @PathVariable Long id,
            @RequestParam(required = false) Integer cantidad,
            @RequestParam(required = false) Double precio) {
        return productoService.actualizarProducto(id, cantidad, precio);
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return "Producto eliminado correctamente";
    }
}
