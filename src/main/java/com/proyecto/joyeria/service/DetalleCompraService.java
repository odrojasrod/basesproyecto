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

    @Transactional
    public DetalleCompra actualizarCompra(Long idCompra, Long idProducto, String emailUsuario) {
        DetalleCompra compra = detalleCompraRepository.findById(idCompra)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        Producto nuevoProducto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Usuario nuevoUsuario = usuarioRepository.findById(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!compra.getProducto().getIdProducto().equals(nuevoProducto.getIdProducto())) {
            Producto antiguoProducto = compra.getProducto();
            antiguoProducto.setCantidad(antiguoProducto.getCantidad() + 1);
            productoRepository.save(antiguoProducto);

            if (nuevoProducto.getCantidad() <= 0) {
                throw new RuntimeException("El nuevo producto no tiene stock disponible.");
            }
            nuevoProducto.setCantidad(nuevoProducto.getCantidad() - 1);
            productoRepository.save(nuevoProducto);

            compra.setProducto(nuevoProducto);
        }

        compra.setUsuario(nuevoUsuario);
        return detalleCompraRepository.save(compra);
    }

    @Transactional
    public void eliminarCompra(Long idCompra) {
        DetalleCompra compra = detalleCompraRepository.findById(idCompra)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        // Devolver el stock del producto
        Producto producto = compra.getProducto();
        producto.setCantidad(producto.getCantidad() + 1);
        productoRepository.save(producto);

        detalleCompraRepository.delete(compra);
    }
}
