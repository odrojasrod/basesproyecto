package com.proyecto.joyeria.controller;

import com.proyecto.joyeria.entity.Imagen;
import com.proyecto.joyeria.service.ImagenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imagenes")
public class ImagenController {

    private final ImagenService imagenService;

    public ImagenController(ImagenService imagenService) {
        this.imagenService = imagenService;
    }

    // Crear imagen
    @PostMapping
    public Imagen crearImagen(@RequestParam String url) {
        return imagenService.crearImagen(url);
    }

    // Listar todas las imágenes
    @GetMapping
    public List<Imagen> listarImagenes() {
        return imagenService.listarImagenes();
    }

    // Obtener imagen por ID
    @GetMapping("/{id}")
    public Imagen obtenerImagen(@PathVariable Long id) {
        return imagenService.obtenerImagen(id);
    }

    // Actualizar URL de imagen
    @PutMapping("/{id}")
    public Imagen actualizarImagen(@PathVariable Long id, @RequestParam String url) {
        return imagenService.actualizarImagen(id, url);
    }

    // Eliminar imagen
    @DeleteMapping("/{id}")
    public String eliminarImagen(@PathVariable Long id) {
        imagenService.eliminarImagen(id);
        return "Imagen eliminada correctamente";
    }
}
