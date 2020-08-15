package com.lanit_tercom.dogfriendly_studproject.data.entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.List;

public class UserEntity {

    private String id;
    private String userName;
    private int age;
    Long timestamp;

    public UserEntity(){}

    public UserEntity(String name, int age){
        this.id = id;
        this.userName = name;
        this.age = age;
    }

    public UserEntity(String id, String name){
        this.id = id;
        this.userName = name;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public java.util.Map<String, String> getTimestamp() {
        return ServerValue.TIMESTAMP;
    }

    @Exclude
    public Long getTimestampForMapper(){
        return timestamp;
    }


    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", userAge=" + age +
                '}' + '\n';
    }
}
