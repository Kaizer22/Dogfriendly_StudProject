package com.lanit_tercom.dogfriendly_studproject.data.firebase.cache;

import com.lanit_tercom.dogfriendly_studproject.data.entity.WalkEntity;

public interface WalkCache {

    void saveWalk(String userId, WalkEntity walkEntity);
}
