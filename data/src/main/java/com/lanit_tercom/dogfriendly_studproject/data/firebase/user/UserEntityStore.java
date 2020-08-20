package com.lanit_tercom.dogfriendly_studproject.data.firebase.user;

import com.lanit_tercom.dogfriendly_studproject.data.entity.PetEntity;
import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
import com.lanit_tercom.domain.exception.ErrorBundle;
import java.util.List;

public interface UserEntityStore {

    interface Error{
        void onError(ErrorBundle errorBundle);
    }

    interface UserByIdCallback extends Error {
        void onUserLoaded(UserEntity user);

    }

    interface UserListCallback extends Error{
        void onUsersListLoaded(List<UserEntity> users);
    }

    interface UserListByIdCallback extends Error{
        void onUserListByIdLoaded(List<UserEntity> users);
    }

    interface UserCreateCallback extends Error{
        void onUserCreated();
    }

    interface UserEditCallback extends Error{
        void onUserEdited();
    }

    interface UserDeleteCallback extends Error{
        void onUserDeleted();
    }

    interface AddPetCallback extends Error{
        void onPetAdded();
    }

    interface DeletePetCallback extends Error{
        void onPetDeleted();
    }

    void getAllUsers(UserListCallback userListCallback);
    void getUserById(String id, UserByIdCallback userByIdCallback);
    void getUserListById(List<String> usersId, UserListByIdCallback userListByIdCallback);
    void createUser(UserEntity user, UserCreateCallback userCreateCallback);
    void editUser(UserEntity user, UserEditCallback userEditCallback);
    void deleteUser(String id, UserDeleteCallback userDeleteCallback);
    void addPet(String id, PetEntity pet, AddPetCallback addPetCallback);
    void deletePet(String userId, String petId, DeletePetCallback deletePetCallback);
}
