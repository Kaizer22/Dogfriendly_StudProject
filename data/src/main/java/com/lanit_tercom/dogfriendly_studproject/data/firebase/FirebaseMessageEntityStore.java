package com.lanit_tercom.dogfriendly_studproject.data.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lanit_tercom.dogfriendly_studproject.data.entity.MessageEntity;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.MessageCache;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FirebaseMessageEntityStore implements MessageEntityStore {

    private static final String CHILD_MESSAGES = "messages";

    private MessageEntity messageEntity = new MessageEntity();

    private MessageCache messageCache; //

    protected DatabaseReference referenceDatabase;

    public FirebaseMessageEntityStore(MessageCache messageCache) {
        this.messageCache = messageCache;
        referenceDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void getAllMessages(MessageListCallback messageListCallback) {
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
                messageListCallback.onMessagesListLoaded(messages); // return all messages from Realtime Database
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void getMessageById(String id, MessageByIdCallback messageByIdCallback) {
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
                messageByIdCallback.onMessageLoaded(messageEntity); // return MessageEntity
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void createMessage(String id, Timestamp timestamp, String userName, String body, MessageCreateCallback messageCreateCallback) {
        MessageEntity messageEntity = new MessageEntity(userName, body);
        messageEntity.setId(id);
        messageEntity.setTimestamp(timestamp);
        referenceDatabase.child(CHILD_MESSAGES).push().setValue(messageEntity, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                messageCreateCallback.onMessageCreated();
            }
        });
    }

    @Override
    public void updateMessage(String id, String body, MessageUpdateCallback messageUpdateCallback) {
        referenceDatabase.child(CHILD_MESSAGES).child(id).child("body").setValue(body, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                messageUpdateCallback.onMessageUpdated();
            }
        });
    }

    @Override
    public void deleteMessage(String id, MessageDeleteCallback messageDeleteCallback) {
        referenceDatabase.child(CHILD_MESSAGES).child(id).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                messageDeleteCallback.onMessageDeleteCallback();
            }
        });
    }

    private void putMessageEntityInCache(String messageId, MessageEntity messageEntity) {
        if (messageCache != null) {
            this.messageCache.saveMessage(messageId, messageEntity);
        }
    }
}