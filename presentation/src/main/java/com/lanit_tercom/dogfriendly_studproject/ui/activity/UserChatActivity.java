package com.lanit_tercom.dogfriendly_studproject.ui.activity;

import android.os.Bundle;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserChatFragment;

import org.jetbrains.annotations.Nullable;

/**
 *  Активность, отображающая UserChatFragment
 *  @author dshebut@rambler.ru
 */
public class UserChatActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);
    }

    @Override
    protected void initializeActivity(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState==null){
            addFragment(R.id.ft_container, new UserChatFragment());
        }

    }
}
