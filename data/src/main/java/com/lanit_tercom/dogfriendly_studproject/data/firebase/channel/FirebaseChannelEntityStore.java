package com.lanit_tercom.dogfriendly_studproject.data.firebase.channel;

import android.util.Log;

import androidx.annotation.NonNull;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lanit_tercom.dogfriendly_studproject.data.entity.ChannelEntity;
import com.lanit_tercom.dogfriendly_studproject.data.exception.RepositoryErrorBundle;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.ChannelCache;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public class FirebaseChannelEntityStore implements ChannelEntityStore{

    private static final String CHILD_CHANNELS = "Channels";
    private ChannelCache channelCache;
    protected DatabaseReference referenceDatabase;


    public FirebaseChannelEntityStore(ChannelCache channelCache) {
        this.channelCache = channelCache;
        this.referenceDatabase = FirebaseDatabase.getInstance().getReference().child(CHILD_CHANNELS);
    }


    @Override
    public void getChannels(String userId, GetChannelsCallback getChannelsCallback) {
        final List<ChannelEntity> channels = new ArrayList<>();
        referenceDatabase.child(userId).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                channels.clear();
                Iterable<DataSnapshot> snapshots = snapshot.getChildren();
                for (DataSnapshot keyNode : snapshots) {
                    ChannelEntity channelEntity = keyNode.getValue(ChannelEntity.class);
                    channelEntity.setId(keyNode.getKey());
                    channels.add(channelEntity);
                }
                getChannelsCallback.onChannelsLoaded(channels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                getChannelsCallback.onError(new RepositoryErrorBundle(databaseError.toException()));
            }
        });
    }


    public String[] getUserIDs(List<HashMap<String, String>> list){
        String[] output = new String[list.size()];
        for(int i = 0; i<output.length;i++)
            output[i] = list.get(i).get("userId");
        return output;
    }


    @Override
    public void addChannel(ChannelEntity channel, AddChannelCallback callback) {
        String[] userIDs = getUserIDs(channel.getMembers());
        DatabaseReference dr = referenceDatabase;

        String firebaseId = dr.push().getKey();
        for(String userId: userIDs){
            Map<String, Object> pair = new HashMap<>();
            channel.setId(firebaseId);
            pair.put(firebaseId, channel);
            dr.child(userId).updateChildren(pair)
                    .addOnSuccessListener(aVoid -> callback.onChannelAdded())
                    .addOnFailureListener(e -> callback.onError(new RepositoryErrorBundle(e)));
        }
    }


    @Override
    public void deleteChannel(String userId, ChannelEntity channel, DeleteChannelCallback callback) {
        String[] users = getUserIDs(channel.getMembers());
        DatabaseReference dr = referenceDatabase;
        for(String id: users)
            if (userId.equals(id))
                dr.child(id).child(channel.getId()).removeValue()
                        .addOnSuccessListener(aVoid -> callback.onChannelDeleted())
                        .addOnFailureListener(e -> callback.onError(new RepositoryErrorBundle(e)));
    }


    private void putChannelEntityInCache(String channelId, ChannelEntity entity) {
        if (channelCache != null) {
            this.channelCache.saveChannel(channelId, entity);
        }
    }
}
