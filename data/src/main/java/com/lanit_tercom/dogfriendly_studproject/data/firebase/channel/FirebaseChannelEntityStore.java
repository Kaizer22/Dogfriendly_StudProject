package com.lanit_tercom.dogfriendly_studproject.data.firebase.channel;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lanit_tercom.dogfriendly_studproject.data.entity.ChannelEntity;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.ChannelCache;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FirebaseChannelEntityStore implements ChannelEntityStore{

    private static final String CHILD_CHANNELS = "Channels";
    private ChannelEntity channelEntity = new ChannelEntity();

    private ChannelCache channelCache;

    protected DatabaseReference referenceDatabase;

    public FirebaseChannelEntityStore(ChannelCache channelCache) {
        this.channelCache = channelCache;
        this.referenceDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void getChannels(ChannelsDetailCallback channelsDetailCallback) {
        final List<ChannelEntity> channels = new ArrayList<>();
        referenceDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //channels.clear(); //?
                List<String> keys = new ArrayList<>();
                Iterable<DataSnapshot> snapshots = snapshot.child(CHILD_CHANNELS).getChildren();
                for (DataSnapshot keyNode : snapshots) {
                    keys.add(keyNode.getKey());

                    ChannelEntity channelEntity = keyNode.getValue(ChannelEntity.class);
                    channelEntity.setId(keyNode.getKey());
                    channels.add(channelEntity);
                }
                channelsDetailCallback.onChannelsLoaded(channels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }


    @Override
    public void addChannel(ChannelEntity channel, ChannelDetailCallback callback) {
        referenceDatabase.child(CHILD_CHANNELS).setValue(channel);
    }

    @Override
    public void deleteChannel(String channelId, ChannelDetailCallback callback) {
        referenceDatabase.child(CHILD_CHANNELS).child(channelId).removeValue();
    }


}
