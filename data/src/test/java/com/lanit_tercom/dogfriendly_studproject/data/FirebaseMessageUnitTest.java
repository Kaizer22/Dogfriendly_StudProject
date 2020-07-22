package com.lanit_tercom.dogfriendly_studproject.data;

import android.util.Log;

import com.lanit_tercom.dogfriendly_studproject.data.entity.MessageEntity;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.message.FirebaseMessageEntityStore;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.message.MessageEntityStore;
import com.lanit_tercom.domain.exception.ErrorBundle;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
// Нужно доделать тест
public class FirebaseMessageUnitTest {
    private MessageEntity messageEntity;
    @Before
    public void initialize(){
        messageEntity = new MessageEntity("Alex", "Test message");
        messageEntity.setId("1");
        messageEntity.setChannelId("1");
        messageEntity.setTimestamp(new Timestamp(10));
    }
    @Test
    public void testPostMessage(){
        new FirebaseMessageEntityStore(null).postMessage(messageEntity, new MessageEntityStore.MessagePostCallback() {
            @Override
            public void onMessagePosted() {
                Log.i("MESSAGE_TEST", messageEntity.getId());
            }

            @Override
            public void onError(ErrorBundle errorBundle) {

            }
        });
    }
}
