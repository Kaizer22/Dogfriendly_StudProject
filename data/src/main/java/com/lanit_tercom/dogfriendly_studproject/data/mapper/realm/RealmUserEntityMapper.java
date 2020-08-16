package com.lanit_tercom.dogfriendly_studproject.data.mapper.realm;

import com.lanit_tercom.dogfriendly_studproject.data.entity.PetEntity;
import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
import com.lanit_tercom.dogfriendly_studproject.data.entity.realm.RealmUserEntity;
import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.library.data.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class RealmUserEntityMapper extends BaseMapper<RealmUserEntity, UserEntity> {

    public RealmUserEntityMapper(){}

    @Override
    public RealmUserEntity map1(UserEntity o2){
        if (o2 == null) {
            return null;
        }
        RealmUserEntity realmUserEntity = new RealmUserEntity();
        realmUserEntity.setId(o2.getId());
        realmUserEntity.setUserName(o2.getName());
        return realmUserEntity;
    }

    @Override
    public UserEntity map2(RealmUserEntity o1) {
        if (o1 == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(o1.getId());
        userEntity.setName(o1.getUserName());
        return userEntity;
    }

    public List<UserEntity> mapForList(List<RealmUserEntity> users){
        if (users == null){
            return null;
        }
        List<UserEntity> usersEntityList = new ArrayList<>();
        for (RealmUserEntity realmUserEntity: users){
            //TODO переделать RealmUserEntityMapper под новый UserEntity
            UserEntity userEntity = new UserEntity(realmUserEntity.getId(),
                    realmUserEntity.getUserName(),
                    realmUserEntity.getAge(), "заглушка в RealmUserEntityMapper",
                    "заглушка в RealmUserEntityMapper",
                    new HashMap<String, PetEntity>(),"ссылка"); // getID, getUserName
            usersEntityList.add(userEntity);
        }
        return usersEntityList;
    }
}