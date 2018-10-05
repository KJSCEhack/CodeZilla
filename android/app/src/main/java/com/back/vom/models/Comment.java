package com.back.vom.models;

public class Comment {

    String userId;
    String userName;
    String commentText;

    public Comment(String userId, String userName, String commentText) {
        this.userId = userId;
        this.userName = userName;
        this.commentText = commentText;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getCommentText() { return commentText; }

    public void setCommentText(String commentText) { this.commentText = commentText; }
}
