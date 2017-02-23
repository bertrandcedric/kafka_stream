package com.java.test.model;

public class Referentiel {

    private Long id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Referentiel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
