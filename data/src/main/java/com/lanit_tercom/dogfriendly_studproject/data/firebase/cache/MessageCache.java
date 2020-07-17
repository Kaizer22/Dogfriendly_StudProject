package com.lanit_tercom.dogfriendly_studproject.data.firebase.cache;

import com.lanit_tercom.dogfriendly_studproject.data.entity.MessageEntity;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.MessageEntityStore;

import java.util.List;

public interface MessageCache extends MessageEntityStore {
    void saveMessages(List<MessageEntity> messageEntities);
}
