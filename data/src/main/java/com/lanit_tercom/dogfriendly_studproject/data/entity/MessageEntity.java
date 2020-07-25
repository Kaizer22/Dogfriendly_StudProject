package com.lanit_tercom.dogfriendly_studproject.data.entity;

import java.sql.Timestamp;
/**
 * @author prostak.sasha111@mail.ru
 */
public class MessageEntity {
    String channelId;
    String id;
    long timestamp;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
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

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}