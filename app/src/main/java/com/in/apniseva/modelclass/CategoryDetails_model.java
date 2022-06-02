package com.in.apniseva.modelclass;

public class CategoryDetails_model {

    String name,id;

    public CategoryDetails_model(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CategoryDetails_model{" +
                name +
                '}';
    }
}
