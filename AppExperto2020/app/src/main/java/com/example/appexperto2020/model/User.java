package com.example.appexperto2020.model;

public class User {
    private String nombreApellido, usuario,eMail,contrasena, descripcion;
    private String intereses;

    public User(String nombreApellido, String usuario, String eMail, String contrasena, String descripcion, String intereses) {
        this.nombreApellido = nombreApellido;
        this.usuario = usuario;
        this.eMail = eMail;
        this.contrasena = contrasena;
        this.descripcion = descripcion;
        this.intereses = intereses;
    }

    public User() {

    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIntereses() {
        return intereses;
    }

    public void setIntereses(String intereses) {
        this.intereses = intereses;
    }
}
