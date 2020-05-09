package com.example.appexperto2020.model;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Expert extends User{

    private long cellphone;
    private HashMap<String,String> jobs;

    //TODAVIA NP
    //private String fotoPerfil;
    //private String fotos;

    public Expert(String id, String firstName, String lastName, String email, String password, String description, String idDocument, String profilePicture, long cellphone, HashMap<String, String> jobs) {
        super(id, firstName, lastName, email, password, description, idDocument, profilePicture);
        this.cellphone = cellphone;
        this.jobs = jobs;
    }
}