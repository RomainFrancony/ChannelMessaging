package com.francony.romain.channelmessaging.model;

/**
 * Created by Romain on 21/01/2017.
 */

public class Message {
    private int userID;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Message{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", soundUrl='" + soundUrl + '\'' +
                ", messageImageUrl='" + messageImageUrl + '\'' +
                '}';
    }

    private String message;
    private String date;
    private String imageUrl;

    public String getSoundUrl() {
        return soundUrl;
    }

    public void setSoundUrl(String soundUrl) {
        this.soundUrl = soundUrl;
    }

    private String soundUrl;

    public String getMessageImageUrl() {
        return messageImageUrl;
    }

    public void setMessageImageUrl(String messageImageUrl) {
        this.messageImageUrl = messageImageUrl;
    }

    private String messageImageUrl;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (userID != message1.userID) return false;
        if (message != null ? !message.equals(message1.message) : message1.message != null)
            return false;
        if (date != null ? !date.equals(message1.date) : message1.date != null) return false;
        if (imageUrl != null ? !imageUrl.equals(message1.imageUrl) : message1.imageUrl != null)
            return false;
        return username != null ? username.equals(message1.username) : message1.username == null;

    }
}
