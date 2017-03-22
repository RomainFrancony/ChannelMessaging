package com.francony.romain.channelmessaging.model;

/**
 * Created by franconr on 23/01/2017.
 */
public class User {
    private int userID;
    private String username;
    private String imageUrl;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(int userid, String username,String imageUrl)
    {
        this.setUserID(userid);
        this.setImageUrl(imageUrl);
        this.setUsername(username);
    }
}
