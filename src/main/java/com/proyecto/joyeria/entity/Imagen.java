package com.proyecto.joyeria.entity;

import jakarta.persistence.*;

@Entity
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImagen;

    @Column(nullable = false, length = 500)
    private String url;

    @OneToOne(mappedBy = "imagen", cascade = CascadeType.ALL)
    private Producto producto;

    // Getters & Setters
    public Long getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Long idImagen) {
        this.idImagen = idImagen;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
