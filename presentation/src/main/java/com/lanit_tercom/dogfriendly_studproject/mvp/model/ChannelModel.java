package com.lanit_tercom.dogfriendly_studproject.mvp.model;

public class ChannelModel {

    private String channelID; //ID диалога

    private String lastMessage; // ID отправителя

    private String lastMessageOwner; // Последнее полученное сообщение (для отображения)

    private String lastMessageTime; // Время последнего полученного сообщения

    public ChannelModel() {
    }

    public ChannelModel(String channelID, String lastMessage, String lastMessageOwner, String lastMessageTime){
        this.channelID = channelID;
        this.lastMessage = lastMessage;
        this.lastMessageOwner = lastMessageOwner;
        this.lastMessageTime = lastMessageTime;
    }


    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelId){
        this.channelID = channelId;
    }

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
