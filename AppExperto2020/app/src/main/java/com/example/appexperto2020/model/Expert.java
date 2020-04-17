package com.example.appexperto2020.model;

import java.util.ArrayList;

public class Expert {
    private String name,identificationCard, phone, email;
    private ArrayList<String> jobs;

    public Expert(String name, String identificationCard, String phone, String email, ArrayList<String> jobs) {
        this.name = name;
        this.identificationCard = identificationCard;
        this.phone = phone;
        this.email = email;
        this.jobs = jobs;
    }

    public Expert() {
    }

    public ArrayList<String> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<String> jobs) {
        this.jobs = jobs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentificationCard() {
        return identificationCard;
    }

    public void setIdentificationCard(String identificationCard) {
        this.identificationCard = identificationCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
