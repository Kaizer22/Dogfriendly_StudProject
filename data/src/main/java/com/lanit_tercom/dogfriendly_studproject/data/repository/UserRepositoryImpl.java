package com.lanit_tercom.dogfriendly_studproject.data.repository;

import com.lanit_tercom.dogfriendly_studproject.data.entity.PetEntity;
import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
import com.lanit_tercom.dogfriendly_studproject.data.exception.RepositoryErrorBundle;
import com.lanit_tercom.dogfriendly_studproject.data.exception.UserListException;
import com.lanit_tercom.dogfriendly_studproject.data.exception.UserNotFoundException;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStore;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.PetEntityDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper;
import com.lanit_tercom.domain.dto.PetDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.repository.UserRepository;
import com.lanit_tercom.domain.dto.UserDto;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private static UserRepositoryImpl INSTANCE;

    public static synchronized UserRepositoryImpl getInstance(UserEntityStoreFactory userEntityStoreFactory,
                                                              UserEntityDtoMapper userEntityDtoMapper) {
        if (INSTANCE == null) {
            INSTANCE = new UserRepositoryImpl(userEntityStoreFactory, userEntityDtoMapper);
        }
        return INSTANCE;
    }

    private final UserEntityStoreFactory userEntityStoreFactory;
    private final UserEntityDtoMapper userEntityDtoMapper;

    public UserRepositoryImpl(UserEntityStoreFactory userEntityStoreFactory,
                                 UserEntityDtoMapper userEntityDtoMapper) {

        if (userEntityStoreFactory == null || userEntityDtoMapper == null) {
            throw new IllegalArgumentException("Invalid null parameters in constructor!!!");
        }

        this.userEntityStoreFactory = userEntityStoreFactory;
        this.userEntityDtoMapper = userEntityDtoMapper;
    }

    @Override
    public void getUserById(final String userId, final UserDetailsCallback userCallback) {
        UserEntityStore userEntityStore = this.userEntityStoreFactory.create();

        userEntityStore.getUserById(userId, new UserEntityStore.UserByIdCallback() {
            @Override
            public void onUserLoaded(UserEntity userEntity) {
                UserDto userDto = UserRepositoryImpl.this.userEntityDtoMapper.map2(userEntity);
                if (userDto != null) {
                    userCallback.onUserLoaded(userDto);
                } else {
                    userCallback.onError(new RepositoryErrorBundle(new UserNotFoundException()));
                }
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                userCallback.onError(errorBundle);
            }
        });
    }


    @Override
    public void getUsers(final UsersDetailsCallback userListCallback) {
        UserEntityStore userEntityStore = this.userEntityStoreFactory.create();

        userEntityStore.getAllUsers(new UserEntityStore.UserListCallback() {
            @Override
            public void onUsersListLoaded(List<UserEntity> users) {
                List<UserDto> usersDtoList = UserRepositoryImpl.this.userEntityDtoMapper.mapForList2(users);
                if (usersDtoList != null){
                    userListCallback.onUsersLoaded(usersDtoList);
                } else {
                    userListCallback.onError(new RepositoryErrorBundle(new UserListException())); // ошибка для списка пользователей
                }
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                userListCallback.onError(errorBundle);
            }
        });
    }

    @Override
    public void createUser(UserDto userDto, UserCreateCallback userCallback) {
        UserEntityStore userEntityStore = this.userEntityStoreFactory.create();
        UserEntity userEntity = userEntityDtoMapper.map1(userDto);

        userEntityStore.createUser(userEntity , new UserEntityStore.UserCreateCallback() {
            @Override
            public void onUserCreated() {
                userCallback.onUserCreated();
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                userCallback.onError(errorBundle);
            }
        });
    }

    @Override
    public void editUser(UserDto userDto, UserEditCallback userCallback) {
        UserEntityStore userEntityStore = this.userEntityStoreFactory.create();
        UserEntity userEntity = userEntityDtoMapper.map1(userDto);

        userEntityStore.editUser(userEntity , new UserEntityStore.UserEditCallback() {

            @Override
            public void onUserEdited() {
                userCallback.onUserEdited();
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                userCallback.onError(errorBundle);
            }
        });
    }

    @Override
    public void deleteUser(String id, UserDeleteCallback userDeleteCallback) {
        UserEntityStore userEntityStore = this.userEntityStoreFactory.create();
        userEntityStore.deleteUser(id, new UserEntityStore.UserDeleteCallback() {
            @Override
            public void onUserDeleted() {
                userDeleteCallback.onUserDeleted();
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                userDeleteCallback.onError(errorBundle);
            }
        });
    }

    @Override
    public void addPet(String id, PetDto pet, AddPetCallback addPetCallback) {
        UserEntityStore userEntityStore = this.userEntityStoreFactory.create();
        PetEntityDtoMapper petEntityDtoMapper = new PetEntityDtoMapper();
        PetEntity petEntity = petEntityDtoMapper.map1(pet);
        userEntityStore.addPet(id, petEntity, new UserEntityStore.AddPetCallback(){

            @Override
            public void onPetAdded() {
                addPetCallback.onPetAdded();
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                addPetCallback.onError(errorBundle);
            }
        });

    }


}
