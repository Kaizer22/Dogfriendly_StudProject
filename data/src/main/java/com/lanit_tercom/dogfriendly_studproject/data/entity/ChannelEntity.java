package com.lanit_tercom.dogfriendly_studproject.data.entity;

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

    public Long getTimestamp() {
        return timestamp;
    }

    public List<HashMap<String, String>> getMembers() {
        return members;
    }

}
