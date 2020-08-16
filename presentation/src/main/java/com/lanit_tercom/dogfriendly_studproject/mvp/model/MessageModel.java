package com.lanit_tercom.dogfriendly_studproject.mvp.model;

import java.util.Date;

/**
 *  Модель данных - "Сообщение"
 *  Время в миллисекундах (System.currentTimeMillis())
 *  @author dshebut@rambler.ru
 */
public class MessageModel {
    private String messageID;

    private String senderID;

    private String chatID;

    private String text;

    private Date time;

    public MessageModel(){

    }
    //Это конструктор используется при тестированиии
    //см. MessageProviderTemp
    public MessageModel(String messageID,
                        String senderID,
                        String chatID,
                        String text){
        this.messageID = messageID;
        this.senderID = senderID;
        this.chatID = chatID;
        this.text = text;

    }


    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
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

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getTime() {
        return time;
    }
}
