package com.lanit_tercom.domain.dto;

public class UserDto {
<<<<<<< HEAD

    private String id;

    private String name;

    private int age;
=======
    private String id;
    private String name;

    public UserDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

>>>>>>> domain_dev

    public String getId() {
        return id;
    }

<<<<<<< HEAD
    public void setId(String id) {
        this.id = id;
    }

=======
>>>>>>> domain_dev
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
<<<<<<< HEAD

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
=======
>>>>>>> domain_dev
}
