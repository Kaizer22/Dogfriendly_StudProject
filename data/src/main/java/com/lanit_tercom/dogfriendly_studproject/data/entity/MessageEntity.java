package com.lanit_tercom.dogfriendly_studproject.data.entity;

import java.sql.Timestamp;

public class MessageEntity {
    String id;
    Timestamp timestamp;
    String userName;
    String body;

    public MessageEntity() {
    }

    public MessageEntity(String userName, String body) {
        this.userName = userName;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
