package com.francony.romain.channelmessaging.model;

import com.francony.romain.channelmessaging.model.Message;

import java.util.ArrayList;

/**
 * Created by Romain on 21/01/2017.
 */

public class Messages {
    private ArrayList<Message> messages;

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }



    @Override
    public int hashCode() {
        return messages != null ? messages.hashCode() : 0;
    }

    public Messages(){
        this.messages = new ArrayList<Message>();
    }
}
