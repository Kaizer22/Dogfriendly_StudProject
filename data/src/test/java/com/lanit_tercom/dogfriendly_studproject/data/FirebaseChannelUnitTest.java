package com.lanit_tercom.dogfriendly_studproject.data;

import android.util.Log;

import com.lanit_tercom.dogfriendly_studproject.data.entity.ChannelEntity;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStore;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.FirebaseChannelEntityStore;

import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//TODO - doesn't work, fails with exception
public class FirebaseChannelUnitTest {
    ChannelEntity entity = new ChannelEntity();

    @Before
    void initialize(){
        entity.setId("1234");
        entity.setLastMessage("1234");
        entity.setLastMessageOwner("1234");
        entity.setName("1234");
        entity.setTimestamp(1234L);
        List<HashMap<String, String>> users = new ArrayList<>();
        HashMap<String, String> first = new HashMap<>();
        HashMap<String, String> second = new HashMap<>();
        first.put("0","user1");
        second.put("1", "user2");
        users.add(first);
        users.add(second);
        entity.setMembers(users);
    }

    @Test
    public void testGetChannels(){
       //todo
    }

    @Test
    public void testAddChannel(){
        //todo
    }

    @Test
    public void testDeleteChannel(){
       //todo
    }
}
