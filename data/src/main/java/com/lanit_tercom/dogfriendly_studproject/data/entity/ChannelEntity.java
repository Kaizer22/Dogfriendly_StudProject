package com.lanit_tercom.dogfriendly_studproject.data.entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.List;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public class ChannelEntity {

    private String id;
    private String name;
    private String lastMessage;
    private String lastMessageOwner;
    private Long timestamp;
    private boolean pinned;
    private boolean offNotification;
    private List<HashMap<String, String>> members;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setLastMessageOwner(String lastMessageOwner) {
        this.lastMessageOwner = lastMessageOwner;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setMembers(List<HashMap<String, String>> members) {
        this.members = members;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getLastMessageOwner() {
        return lastMessageOwner;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public boolean isOffNotification() {
        return offNotification;
    }

    public void setNotification(boolean status) {
        this.offNotification = status;
    }

    public java.util.Map<String, String> getTimestamp() {
        return ServerValue.TIMESTAMP;
    }

    @Exclude
    public Long getTimestampForMapper(){
        return timestamp;
    }


    public List<HashMap<String, String>> getMembers() {
        return members;
    }


}
