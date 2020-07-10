package com.lanit_tercom.domain.repository;

import com.lanit_tercom.domain.dto.UserDto;

import java.util.List;

public interface UserRepository {


    UserDto getUserById(final String userId, Callback callback);

    List<UserDto> getUsers(Callback callback);

    UserDto createUser(String name, Callback callback);

    UserDto editUser(String name, Callback callback);

}