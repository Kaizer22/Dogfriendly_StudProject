package com.lanit_tercom.domain.repository;

import com.lanit_tercom.domain.dto.PetDto;
import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.List;

public interface UserRepository {

    interface Error {
        void onError(ErrorBundle errorBundle);
    }

    interface UserDetailsCallback extends Error {
        void onUserLoaded(UserDto userDto);
    }

    interface UsersDetailsCallback extends Error {
        void onUsersLoaded(List<UserDto> users);
    }

    interface UsersListDetailsCallback extends Error{
        void onUsersListLoaded(List<UserDto> users);
    }

    interface UserCreateCallback extends Error {
        void onUserCreated();
    }

    interface UserEditCallback extends Error {
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



    void getUserById(String userId, UserDetailsCallback userCallback);

    void getUsers(UsersDetailsCallback userCallback);

    void getUserListById(List<String> usersId, UsersListDetailsCallback usersListDetailsCallback);

    void createUser(UserDto userDto, UserCreateCallback userCallback);

    void editUser(UserDto userDto, UserEditCallback userCallback);

    void deleteUser(String id, UserDeleteCallback userDeleteCallback);

    void addPet(String id, PetDto pet, AddPetCallback addPetCallback);

    void deletePet(String userId, String petId, DeletePetCallback deletePetCallback);
}