package com.example.appexperto2020.model;

import java.util.ArrayList;

public class Expert {

    private String id;
    private String username;
    private String contraseña;
    private String cedula;

    private String celular;
    private String email;
    private String jobs;
    private String descripcion;


    //TODAVIA NP
    private String fotoPerfil;
    private String fotos;



    public Expert() {
    }

    public Expert(String id, String username, String contraseña, String cedula, String celular, String email, String jobs, String descripcion) {
        this.id = id;
        this.username = username;
        this.contraseña = contraseña;
        this.cedula = cedula;
        this.celular = celular;
        this.email = email;
        this.jobs = jobs;
        this.descripcion = descripcion;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getFotos() {
        return fotos;
    }

    public void setFotos(String fotos) {
        this.fotos = fotos;
    }

}