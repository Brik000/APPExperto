package com.example.appexperto2020.model;

import com.example.appexperto2020.R;
import com.example.appexperto2020.util.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Service {

    private String clientId;
    private String description;
    private String expertId;
    private String id;
    private double reward;
    private String status;
    private String title;

    public Service(String clientId, String description, String expertId, String id, double reward, String title, String status) {
        this.clientId = clientId;
        this.description = description;
        this.expertId = expertId;
        this.id = id;
        this.reward = reward;
        this.status = status;
        this.title = title;
    }
}
