package com.lanit_tercom.dogfriendly_studproject.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.ResetPasswordFragment;

import org.jetbrains.annotations.Nullable;

public class ResetPasswordActivity extends BaseActivity {

    public static Intent getCallingIntent(Context context){
        return new Intent(context, ResetPasswordActivity.class);
    }

    public void navigateToSignIn(){
        getNavigator().navigateToUserSignIn(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
    }

    @Override
    protected void initializeActivity(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null){
            addFragment(R.id.ft_container, new ResetPasswordFragment());
        }
    }
}
