package com.lanit_tercom.dogfriendly_studproject.mvp.model;

import java.util.List;

public class WalkModel {

    private String id;
    private String walkName;
    private boolean freeAccess;
    private String description;
    private UserModel creator;
    private List<UserModel> members;

    public WalkModel(){
    }

    public WalkModel(String id,
                     String walkName,
                     boolean freeAccess,
                     String description,
                     UserModel creator,
                     List<UserModel> members){
        this.id = id;
        this.walkName =walkName;
        this.freeAccess = freeAccess;
        this.description = description;
        this.creator = creator;
        this.members = members;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWalkName() {
        return walkName;
    }

    public void setWalkName(String walkName) {
        this.walkName = walkName;
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

    public UserModel getCreator() {
        return creator;
    }

    public void setCreator(UserModel creator) {
        this.creator = creator;
    }

    public List<UserModel> getMembers() {
        return members;
    }

    public void setMembers(List<UserModel> members) {
        this.members = members;
    }
}
