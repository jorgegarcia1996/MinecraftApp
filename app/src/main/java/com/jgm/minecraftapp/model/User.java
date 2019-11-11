package com.jgm.minecraftapp.model;

public class User {

    private String nombre, apellidos, email, nacionalidad;

    public User() {
    }

    public User(String nombre, String apellidos, String email, String nacionalidad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.nacionalidad = nacionalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    @Override
    public String toString() {
        return (this.nombre + " " + this.apellidos);
    }
}
