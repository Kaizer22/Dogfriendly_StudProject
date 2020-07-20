package com.lanit_tercom.dogfriendly_studproject.data.firebase.message;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lanit_tercom.dogfriendly_studproject.data.entity.MessageEntity;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.MessageCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FirebaseMessageEntityStore implements MessageEntityStore {

    private static final String CHILD_MESSAGES = "Messages";

    private MessageEntity messageEntity = new MessageEntity();

    private MessageCache messageCache; //

    protected DatabaseReference referenceDatabase;

    public FirebaseMessageEntityStore(MessageCache messageCache) {
        this.messageCache = messageCache;
        referenceDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void getMessages(MessagesDetailCallback messagesDetailCallback) {
        final List<MessageEntity> messages = new ArrayList<>();
        referenceDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();
                List<String> keys = new ArrayList<>();
                Iterable<DataSnapshot> snapshots = dataSnapshot.child(CHILD_MESSAGES).getChildren();
                for (DataSnapshot keyNode : snapshots) {
                    keys.add(keyNode.getKey());
                    MessageEntity messageEntity = keyNode.getValue(MessageEntity.class);
                    messageEntity.setId(keyNode.getKey());
                    messages.add(messageEntity);
                }
                messagesDetailCallback.onMessagesLoaded(messages); // return all messages from Realtime Database
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void getMessage(String id, MessageDetailCallback messageDetailCallback) {
        referenceDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshots = dataSnapshot.child(CHILD_MESSAGES).getChildren();
                for (DataSnapshot snapshot : snapshots) {
                    if (id.equals(snapshot.getKey())) {
                        messageEntity = snapshot.getValue(MessageEntity.class);
                        messageEntity.setId(id);
                    }
                }
                messageDetailCallback.onMessageLoaded(messageEntity); // return MessageEntity
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    private void putMessageEntityInCache(String messageId, MessageEntity messageEntity) {
        if (messageCache != null) {
            this.messageCache.saveMessage(messageId, messageEntity);
        }
    }
}