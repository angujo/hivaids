package com.devindicator.hivaids.models;

/**
 * Created by bangujo on 22/01/2017.
 */

public class NewsItem {
    private String title, link, thumbUrl, source, dateTime;
    private int id, timeStamped;

    public NewsItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeStamped() {
        return timeStamped;
    }

    public void setTimeStamped(int timeStamped) {
        this.timeStamped = timeStamped;
    }
}
