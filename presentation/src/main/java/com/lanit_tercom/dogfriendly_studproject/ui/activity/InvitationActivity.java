package com.lanit_tercom.dogfriendly_studproject.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.InvitationFragment;

import org.jetbrains.annotations.Nullable;

public class InvitationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
    }

    @Override
    protected void initializeActivity(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null){
            addFragment(R.id.ft_container, new InvitationFragment());
        }
    }
}