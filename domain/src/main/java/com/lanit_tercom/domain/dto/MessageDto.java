package com.lanit_tercom.domain.dto;

import java.sql.Timestamp;

/**
 * Класс MessageDto представляющий модель сообщения на domain-слое
 * @author nikolaygorokhov1@gmail.com
 * @author prostak.sasha111@mail.ru
 */
public class MessageDto {
    private String channelId;
    private String id;
    private String userName;
    private String body;
    private Timestamp timestamp;

    public MessageDto(String userName, String body) {
        this.userName = userName;
        this.body = body;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
