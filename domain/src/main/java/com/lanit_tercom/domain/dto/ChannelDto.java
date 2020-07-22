package com.lanit_tercom.domain.dto;

import java.util.List;

public class ChannelDto {

    private String channelId;
    private String lastMessage;
    private String lastMessageOwner;
    private String lastMessageTime;
    //private List<UserDto> members;
    //private String name;


    public ChannelDto(String channelId, String lastMessage, String lastMessageOwner, String lastMessageTime) {//needs more ???
        this.channelId = channelId;
        this.lastMessage = lastMessage;
        this.lastMessageOwner = lastMessageOwner;
        this.lastMessageTime = lastMessageTime;
    }


    public String getChannelId() {return channelId;}


    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageOwner() {
        return lastMessageOwner;
    }

    public void setLastMessageOwner(String lastMessageOwner) {
        this.lastMessageOwner = lastMessageOwner;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}