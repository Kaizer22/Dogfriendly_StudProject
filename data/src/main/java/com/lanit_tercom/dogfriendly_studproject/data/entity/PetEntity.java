package com.lanit_tercom.dogfriendly_studproject.data.entity;

import java.util.List;

public class PetEntity {
    //Поля
    private String id;
    private String name;
    private int age;
    private String breed; //Порода
    private String gender;
    private String about; //Описание
    private List<String> character; //Черты характера
    private List<String> photos;
    private String avatar;



    //Конструктор
    public PetEntity(){}

    public PetEntity(String id, String name, int age, String breed, String gender,
                     String about, List<String> character, List<String> photos, String avatar) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.gender = gender;
        this.about = about;
        this.character = character;
        this.photos = photos;
        this.avatar = avatar;
    }

    //Сеттеры
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setCharacter(List<String> character) {
        this.character = character;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    //Геттеры
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getBreed() {
        return breed;
    }

    public String getGender() {
        return gender;
    }

    public String getAbout() {
        return about;
    }

    public List<String> getCharacter() {
        return character;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public String getAvatar() {
        return avatar;
    }
}
