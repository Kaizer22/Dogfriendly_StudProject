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

import java.util.ArrayList;
import java.util.HashMap;
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

    public boolean isUserInList(String value, List<HashMap<String, String>> list){
        if(list == null) return false;
        for(int i = 0;i<list.size();i++)
            if(list.get(i).containsValue(value)) return true;
        return false;
    }

    @Override
    public void getChannels(String userId, ChannelsDetailCallback channelsDetailCallback) {
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

                    if(isUserInList(userId, channelEntity.getMembers()))
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
        referenceDatabase.child(CHILD_CHANNELS).push().setValue(channel);
    }

    //23:30 я имею право тупить. Что-то с этими id-шниками не заходит совсем...
    @Override
    public void deleteChannel(ChannelEntity entity, ChannelDetailCallback callback) {
        referenceDatabase.child(CHILD_CHANNELS).child(entity.getId()).removeValue();
        //Ни один из вариантов не работает
        //referenceDatabase.child(CHILD_CHANNELS).child(channelId).push().setValue(null);
        //referenceDatabase.child(CHILD_CHANNELS).child(channelId).push().removeValue();
        //referenceDatabase.child(CHILD_CHANNELS).push().child(channelId).removeValue();
        //referenceDatabase.child(CHILD_CHANNELS).push().child(channelId).push().removeValue();
    }


}
