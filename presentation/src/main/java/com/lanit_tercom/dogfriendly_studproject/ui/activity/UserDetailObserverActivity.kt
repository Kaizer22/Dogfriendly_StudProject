package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserDetailFragment
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserDetailObserverFragment

class UserDetailObserverActivity : BaseActivity() {
    private var userId: String? = null
    private var hostUserId: String? = null

    companion object{

        private const val INTENT_EXTRA_PARAM_USER_ID = "INTENT_PARAM_USER_ID"
        private const val INTENT_EXTRA_PARAM_HOST_USER_ID = "INTENT_PARAM_HOST_USER_ID"

        fun getCallingIntent(context: Context, hostUserId: String?, userId: String?): Intent {
            val callingIntent = Intent(context, UserDetailObserverActivity::class.java)
            callingIntent.putExtra(INTENT_EXTRA_PARAM_USER_ID, userId)
            callingIntent.putExtra(INTENT_EXTRA_PARAM_HOST_USER_ID, hostUserId)
            return callingIntent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail_observer)
    }

    //РАСКОМЕНТИТЬ СТРОЧКУ!!! ID ВВЕДЕН ДЛЯ ТЕСТА!!!
    override fun initializeActivity(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            userId = intent.extras?.getString(INTENT_EXTRA_PARAM_USER_ID)
            hostUserId = intent.extras?.getString(INTENT_EXTRA_PARAM_HOST_USER_ID)
//            addFragment(R.id.ft_container, UserDetailFragment(userId))
            addFragment(R.id.ft_container, UserDetailObserverFragment(hostUserId, userId))


        }
    }
}