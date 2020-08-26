package com.lanit_tercom.domain.dto;

import java.util.HashMap;
import java.util.List;

public class ChannelDto {

    private String id;
    private String name;
    private String lastMessage;
    private String lastMessageOwner;
    private Long timestamp;
    private boolean pinned ;
    private boolean offNotification;
    private List<String> members;


    public ChannelDto(){}

    public ChannelDto(String id, String name, String lastMessage, String lastMessageOwner
            , Long timestamp, List<String> members) {
        this.id = id;
        this.name = name;
        this.lastMessage = lastMessage;
        this.lastMessageOwner = lastMessageOwner;
        this.timestamp = timestamp;
        this.members = members;
    }

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

    public void setMembers(List<String> members) {
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

    public Long getTimestamp() {
        return timestamp;
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

    public void setOffNotification(boolean notification) {
        this.offNotification = notification;
    }

    public List<String> getMembers() {
        return members;
    }
}
