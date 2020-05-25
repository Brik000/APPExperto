package com.example.appexperto2020.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
    private String id;
    private String body;
    private String userid;
    private  long timestamp;

}
