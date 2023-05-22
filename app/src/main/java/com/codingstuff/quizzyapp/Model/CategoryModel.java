package com.codingstuff.quizzyapp.Model;

import java.util.UUID;

public class CategoryModel {
    private String id;

    public CategoryModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private UUID uuid;
    private String name;

    public CategoryModel() {
    }

    public CategoryModel(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryModel(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
