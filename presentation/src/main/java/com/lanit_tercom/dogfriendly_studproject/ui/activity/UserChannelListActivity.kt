package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.os.Bundle
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserChannelListFragment

class UserChannelListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_channel_list)
    }

    override fun initializeActivity(savedInstanceState: Bundle?) {
        if(savedInstanceState == null){
            addFragment(R.id.ft_container, UserChannelListFragment())
        }
    }


}