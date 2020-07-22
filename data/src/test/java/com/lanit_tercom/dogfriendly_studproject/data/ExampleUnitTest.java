package com.lanit_tercom.dogfriendly_studproject.data;

import android.util.Log;

import com.lanit_tercom.dogfriendly_studproject.data.entity.ChannelEntity;
import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStore;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.FirebaseChannelEntityStore;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.FirebaseUserEntityStore;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStore;

import org.junit.Test;

import java.util.List;

import org.mockito.Mockito;

import static com.google.firebase.database.FirebaseDatabase.getInstance;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        System.out.println("Ok");
    }



    @Test
    public void testGetChannels(){
        new FirebaseChannelEntityStore(null).getChannels(new ChannelEntityStore.GetChannelsCallback(){

            @Override
            public void onChannelsLoaded(List<ChannelEntity> channels) {
                for(ChannelEntity entity: channels){
                    Log.i("CHANNEL_TEST", entity.getId());
                }
            }

            @Override
            public void onError(Exception exception) {

            }
        });
    }


    @Test
    public void firebaseTest() {

        FirebaseUserEntityStore userEntityStore = Mockito.mock(FirebaseUserEntityStore.class);

        String userId = "1";

        userEntityStore.getUserById(userId, new UserEntityStore.UserByIdCallback() {
            @Override
            public void onUserLoaded(UserEntity userEntity) {
                System.out.println("USER: ");
                System.out.println(userEntity.toString());
            }

            @Override
            public void onError(Exception exception) {
                System.out.println("Error");
            }
        });

        userEntityStore.getAllUsers(new UserEntityStore.UserListCallback(){
            @Override
            public void onUsersListLoaded(List<UserEntity> users) {
                System.out.println("USER: ");
                for (UserEntity user: users){
                    System.out.println(user.toString());
                }
            }

            @Override
            public void onError(Exception exception) {
                System.out.println("Error");
            }
        });
    }
}
