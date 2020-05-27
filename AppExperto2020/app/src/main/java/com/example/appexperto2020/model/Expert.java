package com.example.appexperto2020.model;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Expert extends User {

    private long cellphone;

    private HashMap<String,Job> jobList;

    public Expert(String id, String firstName, String lastName, String email, String password, String description, String idDocument, long cellphone, HashMap<String, Job> jobList) {
        super(id, firstName, lastName, email, password, description, idDocument,null);
        this.cellphone = cellphone;
        this.jobList = jobList;
    }
}