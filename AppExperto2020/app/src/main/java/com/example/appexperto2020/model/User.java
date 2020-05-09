package com.example.appexperto2020.model;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class User {
    private String id, firstName, lastName, email, password, description, idDocument, profilePicture;

}
