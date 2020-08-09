package com.lanit_tercom.dogfriendly_studproject.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.TestSignUpFragment;

import org.jetbrains.annotations.Nullable;

public class TestSignUpActivity extends BaseActivity {

    public static Intent getCallingIntent(Context context){
        return new Intent(context, TestSignUpActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
    }

    public void navigateToUserProfile(){ //Переход в пустой профиль нового
                                         // пользователя по завершении регистрации
        //getNavigator().navigateTo
    }

    public void navigateToSignIn(){
        getNavigator().navigateToUserSignIn(this);
    }

    @Override
    protected void initializeActivity(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null){
            addFragment(R.id.ft_container, new TestSignUpFragment());
        }
    }
}
