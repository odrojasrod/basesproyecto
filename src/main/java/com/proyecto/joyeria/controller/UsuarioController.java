package com.proyecto.joyeria.controller;

import com.proyecto.joyeria.entity.Usuario;
import com.proyecto.joyeria.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.obtenerTodos();
    }

    @GetMapping("/{email}")
    public ResponseEntity<Usuario> obtener(@PathVariable String email) {
        return usuarioService.obtenerPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        return usuarioService.guardar(usuario);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> eliminar(@PathVariable String email) {
        usuarioService.eliminar(email);
        return ResponseEntity.noContent().build();
    }
}
