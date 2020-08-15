package com.lanit_tercom.dogfriendly_studproject.mapper;

import android.net.Uri;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.Point;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel;
import com.lanit_tercom.domain.dto.UserDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class UserDtoModelMapper {
    PetDtoModelMapper mapper = new PetDtoModelMapper();

    public UserDto map1(UserModel userModel){
        return new UserDto(userModel.getId(),
                userModel.getName(),
                userModel.getAge(),
                userModel.getAbout(),
                userModel.getPlans(),
                mapper.fromModelToDtoMap(userModel.getPets()),
                userModel.getAvatar().toString());
    }

    public UserModel map2(UserDto userDto){
        Random random = new Random();

        long x = 20 + (long) (Math.random() * (50 - 20));
        long y = 20 + (long) (Math.random() * (50 - 20));

        Uri avatar = null;
        if(userDto.getAvatar() != null)
            avatar = Uri.parse(userDto.getAvatar());

        return new UserModel(userDto.getId(),
                userDto.getName(),
                userDto.getAge(),
                "none",
                "none",
                userDto.getAbout(),
                userDto.getPlans(),
                avatar,
                mapper.fromDtoToModelList(userDto.getPets()),
                new Point(x, y));
    }


    public List<UserModel> fromDtoToModelList(List<UserDto> users){
        if(users == null) return null;
        List<UserModel> userModelList = new ArrayList<>();
        for(UserDto userDto: users){
            userModelList.add(map2(userDto));
        }
        return userModelList;
    }

    public List<UserDto> fromModelToDtoList(List<UserModel> users){
        if(users == null) return null;
        List<UserDto> userDtoList = new ArrayList<>();
        for(UserModel userModel: users){
            userDtoList.add(map1(userModel));
        }
        return userDtoList;
    }
}

