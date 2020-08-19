package com.lanit_tercom.dogfriendly_studproject.data.entity;

import java.util.HashMap;
import java.util.List;

public class WalkEntity {

    private String walkId;
    private String name;
    private boolean freeAccess;
    private String description;
    private String creator; //UserEntity
    private int radiusOfVisibility;
    private int timeOfVisibility;
    private List<HashMap<String, String>> members;

    public WalkEntity(){}


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

    public List<HashMap<String, String>> getMembers() {
        return members;
    }

    public void setMembers(List<HashMap<String, String>> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "WalkEntity{" +
                "id ='" + walkId + '\'' +
                ", name ='" + name + '\'' +
                ", freeAccess =" + freeAccess + '\'' +
                ", description =" + description + '\'' +
                ", creator =" + creator + '\'' +
                ", members = { " + members + " }"+
                '}' + '\n';
    }


}
