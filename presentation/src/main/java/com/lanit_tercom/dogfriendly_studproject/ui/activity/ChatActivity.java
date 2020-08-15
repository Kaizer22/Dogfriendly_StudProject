package com.lanit_tercom.dogfriendly_studproject.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.ChatFragment;

import org.jetbrains.annotations.Nullable;

/**
 *  Активность, отображающая UserChatFragment
 *  @author dshebut@rambler.ru
 */
public class ChatActivity extends BaseActivity {

    private static final String INTENT_PARAM_CHANNEL_ID = "INTENT_PARAM_CHANNEL_ID";

    public static Intent getCallingIntent(Context context, String channelID){
        Intent callingIntent = new Intent(context, ChatActivity.class);
        callingIntent.putExtra(INTENT_PARAM_CHANNEL_ID, channelID);
        return callingIntent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);
    }

    @Override
    protected void initializeActivity(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState==null){
            String channelID = getIntent().getExtras().getString(INTENT_PARAM_CHANNEL_ID);
            addFragment(R.id.ft_container, new ChatFragment(channelID));
            //addFragment(R.id.ft_container, new ChatFragment());
        }
    }

    public void navigateToChannelsList(){
        //TODO навигация обратно на фрагмент с каналами, а не на
        // фрагмент по умолчанию
       getNavigator().navigateToMainNavigation(this);
    }
}
