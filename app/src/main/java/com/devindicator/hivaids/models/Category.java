package com.devindicator.hivaids.models;

/**
 * Created by bangujo on 22/01/2017.
 */

public class Category {
    private String title, description;
    private int id, position;

    public Category() {
    }

    public Category(int position) {
        this.position = position;
    }

    public Category(String title, String description, int id, int position) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
