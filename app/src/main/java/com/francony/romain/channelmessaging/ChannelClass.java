package com.francony.romain.channelmessaging;

/**
 * Created by franconr on 20/01/2017.
 */
public class ChannelClass {
    private String channelID;
    private String name;
    private String connectedusers;

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConnectedusers() {
        return connectedusers;
    }

    public void setConnectedusers(String connectedusers) {
        this.connectedusers = connectedusers;
    }
}
