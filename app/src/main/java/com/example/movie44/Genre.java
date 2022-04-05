package com.example.movie44;

public class Genre {

    private String id;
    private String name;

    public Genre(String firstName, String lastName) {
        this.id = firstName;
        this.name = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
