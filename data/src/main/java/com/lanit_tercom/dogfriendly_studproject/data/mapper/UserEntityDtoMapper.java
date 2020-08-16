package com.lanit_tercom.dogfriendly_studproject.data.mapper;

import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
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
        userEntity.setTimestamp(userDto.getTimestamp());
//        userEntity.setAge(userDto.getAge());
        return userEntity;
    }

    public UserDto map2(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        UserDto userDto = new UserDto(userEntity.getId(), userEntity.getUserName());
        userDto.setTimestamp(userEntity.getTimestampForMapper());
        return userDto;
    }

    public List<UserDto> mapForList(List<UserEntity> users){
        if (users == null){
            return null;
        }
        List<UserDto> usersDtoList = new ArrayList<>();
        for (UserEntity userEntity: users){
            UserDto userDto = new UserDto(userEntity.getId(), userEntity.getUserName());
            userDto.setTimestamp(userEntity.getTimestampForMapper());
            usersDtoList.add(userDto);
        }
        return usersDtoList;
    }

}
