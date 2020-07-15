package com.lanit_tercom.dogfriendly_studproject.data.repository;

import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
import com.lanit_tercom.dogfriendly_studproject.data.exception.RepositoryErrorBundle;
import com.lanit_tercom.dogfriendly_studproject.data.exception.UserListException;
import com.lanit_tercom.dogfriendly_studproject.data.exception.UserNotFoundException;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.UserEntityStore;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.UserEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper;
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

    protected UserRepositoryImpl(UserEntityStoreFactory userEntityStoreFactory,
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
            public void onError(Exception exception) {
                userCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }


    @Override
    public void createUser(UserDetailsCallback userCallback) {

    }

    @Override
    public void editUserById(String name, UserDetailsCallback userCallback) {

    }

    @Override
    public void getUsers(final UsersDetailsCallback userListCallback) {
        UserEntityStore userEntityStore = this.userEntityStoreFactory.create();

        userEntityStore.getAllUsers(new UserEntityStore.UserListCallback() {
            @Override
            public void onUsersListLoaded(List<UserEntity> users) {
                List<UserDto> usersDtoList = UserRepositoryImpl.this.userEntityDtoMapper.mapForList(users);
                if (usersDtoList != null){
                    userListCallback.onUsersLoaded(usersDtoList);
                } else {
                    userListCallback.onError(new RepositoryErrorBundle(new UserListException())); // ошибка для списка пользователей
                }
            }
            @Override
            public void onError(Exception exception) {
                userListCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }


}
