package com.example.appexperto2020.model;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    String id;
    String name;
    HashMap<String,String> clients, experts;
}
