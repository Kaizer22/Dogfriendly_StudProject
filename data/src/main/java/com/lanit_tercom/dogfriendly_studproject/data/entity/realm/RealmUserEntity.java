package com.lanit_tercom.dogfriendly_studproject.data.entity.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmUserEntity extends RealmObject {

    @PrimaryKey
    private String id;

    private String userName;

    private int age;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getUserName(){
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
}