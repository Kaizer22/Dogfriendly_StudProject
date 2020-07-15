package com.lanit_tercom.data.mapper;

import com.lanit_tercom.data.entity.UserEntity;
import com.lanit_tercom.domain.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public class UserEntityDtoMapper {

    public UserEntityDtoMapper(){}

    public UserEntity map1(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setUserName(userDto.getName());
        userEntity.setAge(userDto.getAge());
        return userEntity;
    }

    public UserDto map2(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setName(userEntity.getUserName());
        userDto.setAge(userEntity.getAge());
        return userDto;
    }

    public List<UserDto> mapForList(List<UserEntity> users){
        if (users == null){
            return null;
        }
        List<UserDto> usersDtoList = new ArrayList<>();
        UserDto userDto = new UserDto();
        for (UserEntity userEntity: users){
            userDto.setId(userEntity.getId());
            userDto.setName(userEntity.getUserName());
            userDto.setAge(userEntity.getAge());
            usersDtoList.add(userDto);
        }
        return usersDtoList;
    }

}
