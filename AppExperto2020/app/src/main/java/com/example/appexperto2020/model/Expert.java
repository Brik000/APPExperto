package com.example.appexperto2020.model;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
public class Expert extends User {

    private long cellphone;

    private HashMap<String,Job> jobList;
    //TODAVIA NP
    //private String fotoPerfil;
    //private String fotos;

    public Expert(String id, String firstName, String lastName, String email, String password, String description, String idDocument, String profilePicture, long cellphone ) {
        super(id, firstName, lastName, email, password, description, idDocument, profilePicture);
        this.cellphone = cellphone;
    }

}