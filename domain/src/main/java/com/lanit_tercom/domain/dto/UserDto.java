package com.lanit_tercom.domain.dto;

public class UserDto {
    private String id;
    private String name;
    private Long timestamp;

    public UserDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
