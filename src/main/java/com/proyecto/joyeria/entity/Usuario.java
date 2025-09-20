package com.proyecto.joyeria.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "contrasena", nullable = false, length = 200)
    private String contrasena;

    @Column(name = "direccion", length = 300)
    private String direccion;

    public Usuario() {}

    public Usuario(String email, String nombre, String contrasena, String direccion) {
        this.email = email;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
