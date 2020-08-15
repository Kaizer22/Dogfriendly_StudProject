package com.lanit_tercom.dogfriendly_studproject.mvp.model;

import java.util.List;

public class WalkModel {

    private String id;
    private String walkName;
    private boolean freeAccess;
    private String description;
    private String creatorId;
    private int radiusOfVisibility;
    private int timeOfVisibility;
    private List<String> members;

    public WalkModel(){
    }

    public WalkModel(String id,
                     String walkName,
                     boolean freeAccess,
                     String description,
                     String creatorId,
                     List<String> members){
        this.id = id;
        this.walkName =walkName;
        this.freeAccess = freeAccess;
        this.description = description;
        this.creatorId = creatorId;
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

    public String getCreator() {
        return creatorId;
    }

    public void setCreator(String creator) {
        this.creatorId = creator;
    }

    public int getRadiusOfVisibility() {
        return radiusOfVisibility;
    }

    public void setRadiusOfVisibility(int radiusOfVisibility) {
        this.radiusOfVisibility = radiusOfVisibility;
    }

    public int getTimeOfVisibility() {
        return timeOfVisibility;
    }

    public void setTimeOfVisibility(int timeOfVisibility) {
        this.timeOfVisibility = timeOfVisibility;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
