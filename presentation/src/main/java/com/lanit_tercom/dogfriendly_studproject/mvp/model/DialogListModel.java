package com.lanit_tercom.dogfriendly_studproject.mvp.model;

public class DialogListModel {

    private String dialogID; //ID диалога

    private String receiverID; // ID отправителя

    private String lastMessage; // Последнее полученное сообщение (для отображения)

    private long lastMessageTime; // Время последнего полученного сообщения

    public DialogListModel() {
    }

    public String getDialogID() {
        return dialogID;
    }

    public void setDialogID(String dialogID) {
        this.dialogID = dialogID;
    }


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

    public long getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(long lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}
