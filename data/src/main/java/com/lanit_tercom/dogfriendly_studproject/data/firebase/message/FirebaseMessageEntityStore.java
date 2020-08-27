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
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.lanit_tercom.dogfriendly_studproject.data.entity.MessageEntity;
import com.lanit_tercom.dogfriendly_studproject.data.exception.RepositoryErrorBundle;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.MessageCache;
import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        referenceDatabase = FirebaseDatabase.getInstance().getReference().child(CHILD_MESSAGES);
    }

    @Override
    public void getMessages(String channelId, MessagesDetailCallback messagesDetailCallback) {
        final List<MessageEntity> messages = new ArrayList<>();
        referenceDatabase.child(channelId).orderByChild("timestamp").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();
                Iterable<DataSnapshot> snapshots = dataSnapshot.getChildren();
                for (DataSnapshot keyNode : snapshots) {
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
    public void getLastMessages(List<String> channelsId, LastMessagesDetailsCallback callback) {
        final List<MessageEntity> lastmessages = new ArrayList<>();
        final List<MessageEntity> messages = new ArrayList<>();
        lastmessages.clear();
        messages.clear();
        for (String channelId : channelsId) {
            referenceDatabase.child(channelId).orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Iterable<DataSnapshot> snapshots = snapshot.getChildren();
                    for (DataSnapshot keyNode : snapshots) {
                        MessageEntity messageEntity = keyNode.getValue(MessageEntity.class);
                        messages.add(messageEntity);
                    }
                    //Это решает проблему с пустым каналом
                    if (!messages.isEmpty()){
                        lastmessages.add(messages.get(messages.size()-1));
                    }
                    callback.onLastMessagesLoaded(lastmessages);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    callback.onError(new RepositoryErrorBundle(error.toException()));
                }
            });
        }
    }

    @Override
    public void postMessage(MessageEntity messageEntity, MessagePostCallback messagePostCallback) {

        String channelId = messageEntity.getChannelId();
        referenceDatabase.child(channelId).push().setValue(messageEntity).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        referenceDatabase.child(channelId).child(id).setValue(messageEntity).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        referenceDatabase.child(channelId).child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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