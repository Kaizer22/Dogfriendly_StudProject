package com.lanit_tercom.domain.repository;

import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

public interface Callback {

    void onUserLoaded(UserDto userDto);

    void onError(ErrorBundle errorBundle);
}
