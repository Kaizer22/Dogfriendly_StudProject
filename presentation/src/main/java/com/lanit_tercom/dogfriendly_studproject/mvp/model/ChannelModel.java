package com.lanit_tercom.dogfriendly_studproject.mvp.model;

public class ChannelModel {

    private String dialogID; //ID диалога

    private String receiverID; // ID отправителя

    private String lastMessage; // Последнее полученное сообщение (для отображения)

    private String lastMessageTime; // Время последнего полученного сообщения

    public ChannelModel() {
    }

    public ChannelModel(String dialogID, String receiverID, String lastMessage, String lastMessageTime){
        this.dialogID = dialogID;
        this.receiverID = receiverID;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }

    public String getDialogID() {
        return dialogID;
    }

    public void setDialogID(String dialogID) {
        this.dialogID = dialogID;
    }

    //TODO Name or ID
    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}
