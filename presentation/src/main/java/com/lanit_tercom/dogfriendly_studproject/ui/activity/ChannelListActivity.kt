package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.ChannelListFragment

class ChannelListActivity : BaseActivity() {

    companion object{

        private const val INTENT_PARAM_USER_ID = "INTENT_PARAM_USER_ID"

        fun getCallingIntent(context: Context, userId: String?): Intent {
            val callingIntent = Intent(context, ChannelListActivity::class.java)
            callingIntent.putExtra(INTENT_PARAM_USER_ID, userId)
            return callingIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel_list)
    }

    override fun initializeActivity(savedInstanceState: Bundle?) {
        if(savedInstanceState == null){
            addFragment(R.id.ft_container, ChannelListFragment())
        }
    }

    //TODO channelId: String
    fun navigateToChat() =
            navigator?.navigateToChat(this, "-MCqwIrhuEPqkgz1GV18" ); //123123123 -MCqwIrhuEPqkgz1GV18


}