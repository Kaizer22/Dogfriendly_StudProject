package com.lanit_tercom.dogfriendly_studproject.data.mapper;

import com.lanit_tercom.dogfriendly_studproject.data.entity.PetEntity;
import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
import com.lanit_tercom.domain.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public class UserEntityDtoMapper {
    PetEntityDtoMapper petEntityDtoMapper = new PetEntityDtoMapper();

    public UserEntityDtoMapper(){}

    public UserEntity map1(UserDto userDto) {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(userDto.getId());
        userEntity.setName(userDto.getName());
        userEntity.setAge(userDto.getAge());
        userEntity.setAbout(userDto.getAbout());
        userEntity.setPlans(userDto.getPlans());
        userEntity.setPets(petEntityDtoMapper.fromDtoToEntityList(userDto.getPets()));
        userEntity.setAvatar(userDto.getAvatar());

        return userEntity;
    }

    public UserDto map2(UserEntity userEntity) {
        UserDto userDto = new UserDto();

        userDto.setId(userEntity.getId());
        userDto.setName(userEntity.getName());
        userDto.setAge(userEntity.getAge());
        userDto.setAbout(userEntity.getAbout());
        userDto.setPlans(userEntity.getPlans());
        userDto.setPets(petEntityDtoMapper.fromEntityToDtoList(userEntity.getPets()));
        userDto.setAvatar(userEntity.getAvatar());

        return userDto;
    }

    public List<UserEntity> mapForList(List<UserDto> users){
        if(users == null) return null;
        List<UserEntity> userEntityList = new ArrayList<>();
        for(UserDto userDto: users){
            UserEntity userEntity = map1(userDto);
            userEntityList.add(userEntity);
        }
        return userEntityList;
    }

    public List<UserDto> mapForList2(List<UserEntity> users){
        if(users == null) return null;
        List<UserDto> usersDtoList = new ArrayList<>();
        for(UserEntity userEntity: users){
            UserDto userDto = map2(userEntity);
            usersDtoList.add(userDto);
        }
        return usersDtoList;
    }

}
