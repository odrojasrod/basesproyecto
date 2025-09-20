package com.proyecto.joyeria.service;

import com.proyecto.joyeria.entity.Imagen;
import com.proyecto.joyeria.repository.ImagenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImagenService {

    private final ImagenRepository imagenRepository;

    public ImagenService(ImagenRepository imagenRepository) {
        this.imagenRepository = imagenRepository;
    }

    // Crear imagen
    @Transactional
    public Imagen crearImagen(String url) {
        Imagen img = new Imagen();
        img.setUrl(url);
        return imagenRepository.save(img);
    }

    // Listar todas las imágenes
    public List<Imagen> listarImagenes() {
        return imagenRepository.findAll();
    }

    // Obtener imagen por ID
    public Imagen obtenerImagen(Long id) {
        return imagenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imagen no encontrada"));
    }

    // Actualizar URL
    @Transactional
    public Imagen actualizarImagen(Long id, String url) {
        Imagen img = obtenerImagen(id);
        img.setUrl(url);
        return imagenRepository.save(img);
    }

    // Eliminar imagen
    public void eliminarImagen(Long id) {
        imagenRepository.deleteById(id);
    }
}
