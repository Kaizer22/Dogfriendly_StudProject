package com.lanit_tercom.domain.dto;

/**
 * Класс MessageDto представляющий модель сообщения на domain-слое
 * @author nikolaygorokhov1@gmail.com
 */
public class MessageDto {
    private String messageId;
    private String senderId;
    private String receiverId;
    private String text;
    private long timestamp;

    public MessageDto(String messageId, String senderId, String receiverId, String text, long timestamp) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getText() {
        return text;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
