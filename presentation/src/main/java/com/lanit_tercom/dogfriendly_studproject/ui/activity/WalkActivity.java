package com.lanit_tercom.dogfriendly_studproject.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.WalkFragment;

import org.jetbrains.annotations.Nullable;

public class WalkActivity extends BaseActivity {

    private static final String INTENT_PARAM_USER_ID = "INTENT_PARAM_USER_ID";

    public static Intent getCallingIntent(Context context, String userID){
        Intent callingIntent = new Intent(context, WalkActivity.class);
        callingIntent.putExtra(INTENT_PARAM_USER_ID, userID);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);
    }

    @Override
    protected void initializeActivity(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null){
            addFragment(R.id.ft_container, new WalkFragment());
        }
    }
}