package com.back.vom.models;

public class Report {

    private String title;
    private String comment;

    public Report() {}

    public Report(String title, String comment) {
        this.title = title;
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
