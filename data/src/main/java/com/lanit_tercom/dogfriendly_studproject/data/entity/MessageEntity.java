package com.lanit_tercom.dogfriendly_studproject.data.entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.sql.Timestamp;
import java.util.Map;

/**
 * @author prostak.sasha111@mail.ru
 */
public class MessageEntity {
    String channelId;
    String id;
    Long timestamp;
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

    public java.util.Map<String, String> getTimestamp() {
        return ServerValue.TIMESTAMP;
    }

    @Exclude
    public Long getTimestampForMapper(){
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
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
