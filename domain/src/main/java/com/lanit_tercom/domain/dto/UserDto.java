package com.lanit_tercom.domain.dto;

import java.util.List;

public class UserDto {
    //Поля
    private String id;
    private String name;
    private int age;
    private String about;
    private String plans;
    private List<PetDto> pets;
    private String avatar;

    //Конструктор
    public UserDto(){}

    public UserDto(String id, String name, int age, String about, String plans, List<PetDto> pets, String avatar) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.about = about;
        this.plans = plans;
        this.pets = pets;
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

    public void setAbout(String about) {
        this.about = about;
    }

    public void setPlans(String plans) {
        this.plans = plans;
    }

    public void setPets(List<PetDto> pets) {
        this.pets = pets;
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

    public String getAbout() {
        return about;
    }

    public String getPlans() {
        return plans;
    }

    public List<PetDto> getPets() {
        return pets;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", userName='" + name + '\'' +
                ", userAge=" + age +
                '}' + '\n';
    }
}
