package com.proyecto.joyeria.service;

import com.proyecto.joyeria.entity.DetalleCompra;
import com.proyecto.joyeria.entity.Producto;
import com.proyecto.joyeria.entity.Usuario;
import com.proyecto.joyeria.repository.DetalleCompraRepository;
import com.proyecto.joyeria.repository.ProductoRepository;
import com.proyecto.joyeria.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleCompraService {

    private final DetalleCompraRepository detalleCompraRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public DetalleCompraService(DetalleCompraRepository detalleCompraRepository,
                                ProductoRepository productoRepository,
                                UsuarioRepository usuarioRepository) {
        this.detalleCompraRepository = detalleCompraRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<DetalleCompra> obtenerTodas() {
        return detalleCompraRepository.findAll();
    }

    public Optional<DetalleCompra> obtenerPorId(Long id) {
        return detalleCompraRepository.findById(id);
    }

    @Transactional
    public DetalleCompra comprarProducto(Long idProducto, String emailUsuario) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Usuario usuario = usuarioRepository.findById(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (producto.getCantidad() <= 0) {
            throw new RuntimeException("El producto no tiene stock disponible.");
        }

        // Descontar stock
        producto.setCantidad(producto.getCantidad() - 1);
        productoRepository.save(producto);

        // Crear detalle de compra
        DetalleCompra detalleCompra = new DetalleCompra(producto, usuario);
        return detalleCompraRepository.save(detalleCompra);
    }
}
