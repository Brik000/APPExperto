package com.example.appexperto2020.model;

import java.util.HashMap;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Client extends User {

    private HashMap<String, String> interests;

    public Client(String id, String firstName, String lastName, String email, String password, String description, String idDocument, String profilePicture, HashMap<String, String> interests) {
        super(id, firstName, lastName, email, password, description, idDocument, profilePicture);
        this.interests = interests;
    }
}
