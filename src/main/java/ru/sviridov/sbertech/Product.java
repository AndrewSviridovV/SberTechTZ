package ru.sviridov.sbertech;

import org.hibernate.annotations.Entity;

import javax.persistence.Id;

@Entity
public class Product {

    @Id
    private String id;

    private String name;

    private String attribute;

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

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
