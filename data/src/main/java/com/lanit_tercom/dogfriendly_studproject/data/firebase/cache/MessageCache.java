package com.lanit_tercom.dogfriendly_studproject.data.firebase.cache;

import com.lanit_tercom.dogfriendly_studproject.data.entity.MessageEntity;

public interface MessageCache {
    void saveMessage(String messageId, MessageEntity messageEntity);
}
