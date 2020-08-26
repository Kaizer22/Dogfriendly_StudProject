package com.lanit_tercom.dogfriendly_studproject.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.ChatFragment;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 *  Активность, отображающая UserChatFragment
 *  @author dshebut@rambler.ru
 */
public class ChatActivity extends BaseActivity {
    private ChatFragment chatFragment;

    private static final String INTENT_PARAM_CHANNEL_ID = "INTENT_PARAM_CHANNEL_ID";
    private static final String INTENT_PARAM_MEMBERS = "INTENT_PARAM_MEMBERS";
    private static final String INTENT_PARAM_CHANNEL_NAME = "INTENT_PARAM_CHANNEL_NAME";

    public static Intent getCallingIntent(Context context, String channelID,
                                          String channelName, List<String> members){
        Intent callingIntent = new Intent(context, ChatActivity.class);
        callingIntent.putExtra(INTENT_PARAM_CHANNEL_ID, channelID);
        callingIntent.putExtra(INTENT_PARAM_CHANNEL_NAME, channelName);
        callingIntent.putStringArrayListExtra(INTENT_PARAM_MEMBERS, (ArrayList<String>)members);
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
            ChannelModel channelModel = new ChannelModel();
            channelModel.setId(getIntent().getExtras().getString(INTENT_PARAM_CHANNEL_ID));
            channelModel.setMembers(getIntent().getExtras().getStringArrayList(INTENT_PARAM_MEMBERS));
            channelModel.setName(getIntent().getExtras().getString(INTENT_PARAM_CHANNEL_NAME));

            chatFragment = new ChatFragment(channelModel);
            getSupportFragmentManager().beginTransaction().addToBackStack(null)
                    .replace(R.id.ft_container, chatFragment)
                    .commit();
            //addFragment(R.id.ft_container, new ChatFragment());
        }
    }

    public void navigateToChannelsList(){
       getNavigator().navigateToMainNavigation(this);
    }

    public void navigateToUserDetailObserver(String hostId, String addresseeId){
        getNavigator().navigateToUserDetailObserver(this, hostId, addresseeId );
    }
}
