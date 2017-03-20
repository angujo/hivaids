package com.devindicator.hivaids.models;

/**
 * Created by bangujo on 20/01/2017.
 */

public class ChatMessage {
    private String content, senderName, id, Date, Time;
    private int receiverID, senderID;
    private boolean isMine;

    public ChatMessage(String content, String senderName,  int receiverID, int senderID, boolean isMine) {
        this.content = content;
        this.senderName = senderName;
        this.id = id;
        this.receiverID = receiverID;
        this.senderID = senderID;
        this.isMine = isMine;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
