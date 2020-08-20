package com.lanit_tercom.dogfriendly_studproject.data.firebase.cache;

import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStore;

public interface UserCache extends UserEntityStore{ // extends UserEntityStore

    void saveUser(String userId, UserEntity userEntity);
}
