package com.lanit_tercom.dogfriendly_studproject.ui.activity;

import android.os.Bundle;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.WelcomeFragment;

import org.jetbrains.annotations.Nullable;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void initializeActivity(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState==null){
            addFragment(R.id.ft_container, new WelcomeFragment());
        }
    }

    public void navigateToUserSignIn(){
        getNavigator().navigateToUserSignIn(this);
    }

    public void navigateToUserSignUp(){
        getNavigator().navigateToUserSignUp(this);
    }
}
