package com.example.appexperto2020.model;

public class Job {
    String id;
    String name;
    Expert[] experts;
    User[] users;

    public Job(String id, String name, Expert[] experts, User[] users) {
        this.id = id;
        this.name = name;
        this.experts = experts;
        this.users = users;
    }

    public Job() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Expert[] getExperts() {
        return experts;
    }

    public void setExperts(Expert[] experts) {
        this.experts = experts;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }
}
