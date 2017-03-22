package com.francony.romain.channelmessaging.model;

/**
 * Created by Romain on 28/01/2017.
 */

public class PrivateMessageClass {
    private String userID;
    private String sendbyme;
    private String username;
    private String message;
    private String date;
    private String imageUrl;
    private String everRead;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSendbyme() {
        return sendbyme;
    }

    public void setSendbyme(String sendbyme) {
        this.sendbyme = sendbyme;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEverRead() {
        return everRead;
    }

    public void setEverRead(String everRead) {
        this.everRead = everRead;
    }
}
