package com.lanit_tercom.dogfriendly_studproject.data.firebase.cache;

import com.lanit_tercom.dogfriendly_studproject.data.entity.ChannelEntity;

public interface ChannelCache {
    void saveChannel(String messageId, ChannelEntity channelEntity);
}
