package com.lanit_tercom.domain.dto;

import java.util.List;

public class WalkDto {

    private String walkId;
    private String name;
    private boolean freeAccess;
    private String description;
    private String creator;
    private List<String> members;

    public String getWalkId() {
        return walkId;
    }

    public void setWalkId(String walkId) {
        this.walkId = walkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFreeAccess() {
        return freeAccess;
    }

    public void setFreeAccess(boolean freeAccess) {
        this.freeAccess = freeAccess;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
