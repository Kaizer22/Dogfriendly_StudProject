package com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.realm;

import com.lanit_tercom.dogfriendly_studproject.data.entity.PetEntity;
import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
import com.lanit_tercom.dogfriendly_studproject.data.entity.realm.RealmUserEntity;
import com.lanit_tercom.dogfriendly_studproject.data.exception.RepositoryErrorBundle;
//import com.lanit_tercom.dogfriendly_studproject.data.entity.realm.RealmUserEntity;
import com.lanit_tercom.dogfriendly_studproject.data.exception.UserListException;
import com.lanit_tercom.dogfriendly_studproject.data.exception.UserNotFoundException;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.UserCache;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.realm.RealmUserEntityMapper;

import java.util.List;

import io.realm.Realm;

public class RealmUserCache implements UserCache {

    /*private RealmUserEntityMapper realmUserEntityMapper;*/

    public RealmUserCache(RealmUserEntityMapper realmUserEntityMapper) {
        //this.realmUserEntityMapper = realmUserEntityMapper;
    }


    @Override
    public void saveUser(String userId, UserEntity userEntity) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            //realm.copyToRealmOrUpdate(realmUserEntityMapper.map1(userEntity));
        });
    }

    public void getAllUsers(UserListCallback userListCallback) { //
        Realm realm = Realm.getDefaultInstance();
        List<RealmUserEntity> userEntityList = realm.where(RealmUserEntity.class).findAll();

        if (userEntityList != null){
            //userListCallback.onUsersListLoaded(realmUserEntityMapper.mapForList(userEntityList));
        }
        else {
            //userListCallback.onError(new UserListException());
        }
    }


    public void getUserById(String id, UserByIdCallback userByIdCallback) {
        Realm realm = Realm.getDefaultInstance();
        RealmUserEntity userEntity = realm.where(RealmUserEntity.class).equalTo("id", id).findFirst();

        if (userEntity != null){
            //userByIdCallback.onUserLoaded(realmUserEntityMapper.map2(userEntity));
        }
        else {
            //userByIdCallback.onError(new UserNotFoundException());
        }
    }

    @Override
    public void getUserListById(List<String> usersId, UserListByIdCallback userListByIdCallback) {

    }


    public void createUser(UserEntity user, UserCreateCallback userCreateCallback) {

    }


    public void editUser(UserEntity user, UserEditCallback userEditCallback) {

    }


    public void deleteUser(String id, UserDeleteCallback userDeleteCallback) {

    }


    public void addPet(String id, PetEntity pet, AddPetCallback addPetCallback) {

    }


    public void deletePet(String userId, String petId, DeletePetCallback deletePetCallback) {

    }
}
