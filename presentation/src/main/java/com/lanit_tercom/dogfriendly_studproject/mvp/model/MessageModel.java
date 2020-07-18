package com.lanit_tercom.dogfriendly_studproject.mvp.model;

/**
 *  Модель данных - "Сообщение"
 *  Время в миллисекундах (System.currentTimeMillis())
 *  @author dshebut@rambler.ru
 */
public class MessageModel {
    private String messageID;

    private String senderID;

    private String receiverID;

    private String text;

    private long time;

    public MessageModel(){

    }
    //Это конструктор используется при тестированиии
    //см. MessageProviderTemp
    public MessageModel(String messageID,
                        String senderID,
                        String receiverID,
                        String text,
                        long time){
        this.messageID = messageID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.text = text;
        this.time = time;

    }


    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
