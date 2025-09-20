package com.proyecto.joyeria.controller;

import com.proyecto.joyeria.entity.DetalleCompra;
import com.proyecto.joyeria.service.DetalleCompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compras")
public class DetalleCompraController {

    private final DetalleCompraService detalleCompraService;

    public DetalleCompraController(DetalleCompraService detalleCompraService) {
        this.detalleCompraService = detalleCompraService;
    }

    @GetMapping
    public List<DetalleCompra> listar() {
        return detalleCompraService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleCompra> obtener(@PathVariable Long id) {
        return detalleCompraService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/comprar")
    public ResponseEntity<DetalleCompra> comprar(@RequestParam Long idProducto, @RequestParam String emailUsuario) {
        try {
            DetalleCompra detalleCompra = detalleCompraService.comprarProducto(idProducto, emailUsuario);
            return ResponseEntity.ok(detalleCompra);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
