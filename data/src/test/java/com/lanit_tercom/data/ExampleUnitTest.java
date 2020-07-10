package com.lanit_tercom.data;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lanit_tercom.data.entity.UserEntity;
import com.lanit_tercom.data.firebase.FirebaseEntityStore;
import com.lanit_tercom.data.firebase.UserEntityStore;
import com.lanit_tercom.data.mapper.UserEntityDtoMapper;
import com.lanit_tercom.domain.dto.UserDto;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mock;
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
    public void firebaseTest() {

        FirebaseEntityStore userEntityStore = Mockito.mock(FirebaseEntityStore.class);

        final UserEntityDtoMapper userEntityDtoMapper = new UserEntityDtoMapper();

        String userId = "1";

        userEntityStore.getUserById(userId, new UserEntityStore.DataStatus() {
            @Override
            public void userEntityLoaded(UserEntity userEntity) {
                UserDto userDto = userEntityDtoMapper.map2(userEntity);
                System.out.println("Success");
            }

            @Override public void allUsersLoaded(List<UserEntity> users) {
                for (UserEntity userEntity: users){
                    UserDto userDto = userEntityDtoMapper.map2(userEntity);
                    System.out.println("Success");
                }
            }
        });
    }
}
