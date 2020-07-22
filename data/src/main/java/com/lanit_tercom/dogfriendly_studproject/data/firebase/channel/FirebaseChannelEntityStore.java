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
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public class FirebaseChannelEntityStore implements ChannelEntityStore{

    private static final String CHILD_CHANNELS = "Channels";
    private ChannelEntity channelEntity = new ChannelEntity();

    private ChannelCache channelCache;

    protected DatabaseReference referenceDatabase;

    public FirebaseChannelEntityStore(ChannelCache channelCache) {
        this.channelCache = channelCache;
        this.referenceDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public String[] getUserIDs(List<HashMap<String, String>> list){
        String[] output = new String[list.size()];
        for(int i = 0; i<output.length;i++)
            output[i] = list.get(i).get("userId");
        return output;
    }

    @Override
    public void getChannels(String userId, ChannelsDetailCallback channelsDetailCallback) {
        final List<ChannelEntity> channels = new ArrayList<>();
        referenceDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //channels.clear(); //?
                List<String> keys = new ArrayList<>();
                Iterable<DataSnapshot> snapshots = snapshot.child(CHILD_CHANNELS).child(userId).getChildren();
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
        String[] userIDs = getUserIDs(channel.getMembers());
        DatabaseReference dr = referenceDatabase.child(CHILD_CHANNELS);

        String firebaseId = dr.push().getKey();

        //Тут наверное можно по красивее сделать как то. А может и нет... но вроде работает.
        Map<String, Object> map = new HashMap<>();
        for(String userId: userIDs){
            Map<String, ChannelEntity> pair = new HashMap<>();
            pair.put(firebaseId, channel);
            map.put(userId, pair);
        }

        //Все одновременно одним вызовом.
        dr.updateChildren(map);
    }

    @Override
    public void deleteChannel(ChannelEntity channel, ChannelDetailCallback callback) {
        String[] users = getUserIDs(channel.getMembers());
        DatabaseReference dr = referenceDatabase.child(CHILD_CHANNELS);
        for(String id: users){
            dr.child(id).child(channel.getId()).removeValue();
        }

    }

    private void putChannelEntityInCache(String channelId, ChannelEntity entity) {
        if (channelCache != null) {
            this.channelCache.saveChannel(channelId, entity);
        }
    }
}
