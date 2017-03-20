package com.devindicator.hivaids.models;

/**
 * Created by bangujo on 20/01/2017.
 */

public class ChatGroup {
    private int id;
    private String name;
    private String duration;
    private String lastConversation;

    public ChatGroup() {
    }

    public ChatGroup(String named, String lastConvo) {
        name = named;
        lastConversation = lastConvo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLastConversation() {
        return lastConversation;
    }

    public void setLastConversation(String lastConversation) {
        this.lastConversation = lastConversation;
    }
}
