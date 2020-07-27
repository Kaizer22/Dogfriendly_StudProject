package com.lanit_tercom.dogfriendly_studproject.data.firebase.message;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lanit_tercom.dogfriendly_studproject.data.entity.MessageEntity;
import com.lanit_tercom.dogfriendly_studproject.data.exception.RepositoryErrorBundle;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.MessageCache;
import com.lanit_tercom.domain.exception.ErrorBundle;

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
    public void getMessages(String channelId, MessagesDetailCallback messagesDetailCallback) {
        final List<MessageEntity> messages = new ArrayList<>();
        referenceDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();
                List<String> keys = new ArrayList<>();
                Iterable<DataSnapshot> snapshots = dataSnapshot.child(CHILD_MESSAGES).child(channelId).getChildren();
                for (DataSnapshot keyNode : snapshots) {
                    keys.add(keyNode.getKey());
                    MessageEntity messageEntity = keyNode.getValue(MessageEntity.class);
                    messageEntity.setId(keyNode.getKey());
                    messages.add(messageEntity);
                }
                messagesDetailCallback.onMessagesLoaded(messages); // return all messages from Realtime Database with channelId
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                messagesDetailCallback.onError(new RepositoryErrorBundle(databaseError.toException()));
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
                messageDetailCallback.onError(new RepositoryErrorBundle(databaseError.toException()));
            }
        });
    }
    @Override
    public void postMessage(MessageEntity messageEntity, MessagePostCallback messagePostCallback) {

        String channelId = messageEntity.getChannelId();
        referenceDatabase.child(CHILD_MESSAGES).child(channelId).push().setValue(messageEntity).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                messagePostCallback.onMessagePosted();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                messagePostCallback.onError(new RepositoryErrorBundle(e));
            }
        });
    }

    @Override
    public void editMessage(MessageEntity messageEntity, MessageEditCallback messageEditCallback) {
        String id = messageEntity.getId();
        String channelId = messageEntity.getChannelId();
        referenceDatabase.child(CHILD_MESSAGES).child(channelId).child(id).setValue(messageEntity).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                messageEditCallback.onMessageEdited();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                messageEditCallback.onError(new RepositoryErrorBundle(e));
            }
        });
    }

    @Override
    public void deleteMessage(MessageEntity messageEntity, MessageDeleteCallback messageDeleteCallback) {
        String id = messageEntity.getId();
        String channelId = messageEntity.getChannelId();
        referenceDatabase.child(CHILD_MESSAGES).child(channelId).child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                messageDeleteCallback.onMessageDeleted();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                messageDeleteCallback.onError(new RepositoryErrorBundle(e));
            }
        });
    }

    private void putMessageEntityInCache(String messageId, MessageEntity messageEntity) {
        if (messageCache != null) {
            this.messageCache.saveMessage(messageId, messageEntity);
        }
    }
}