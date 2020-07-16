package com.lanit_tercom.dogfriendly_studproject.data.entity;

public class UserEntity {

    private String id;
    private String userName;
    private int age;

    public UserEntity(){}

    public UserEntity(String name, int age){
        this.id = id;
        this.userName = name;
        this.age = age;
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

    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", userAge=" + age +
                '}' + '\n';
    }
}
