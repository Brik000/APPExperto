package com.example.appexperto2020.model;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Client extends User {

    private HashMap<String, Job> interests;

    public Client(String id, String firstName, String lastName, String email, String password, String description, String idDocument, HashMap<String, String> services, HashMap<String, Job> interests) {
        super(id, firstName, lastName, email, password, description, idDocument, services);
        this.interests = interests;
    }
}
