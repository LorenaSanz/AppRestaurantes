package com.example.apprestaurantes;


public class Restaurante {
    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String comentarios;
    private int valoracion;

    //Constructor completo
    public Restaurante(int id, String nombre, String direccion, String telefono, String comentarios, int valoracion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.comentarios = comentarios;
        this.valoracion = valoracion;
    }

    //Constructor sin id (para nuevos restaurantes antes de guardarlos en la base de datos)
    public Restaurante(String nombre, String direccion, String telefono, String comentarios, int valoracion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.comentarios = comentarios;
        this.valoracion = valoracion;
    }

    //Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }
}
