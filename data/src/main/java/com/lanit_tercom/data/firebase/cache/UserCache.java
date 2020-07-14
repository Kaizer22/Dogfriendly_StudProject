package com.lanit_tercom.data.firebase.cache;

import com.lanit_tercom.data.entity.UserEntity;
import com.lanit_tercom.data.firebase.UserEntityStore;

public interface UserCache extends UserEntityStore {

    void saveUser(String userId, UserEntity userEntity);
}
