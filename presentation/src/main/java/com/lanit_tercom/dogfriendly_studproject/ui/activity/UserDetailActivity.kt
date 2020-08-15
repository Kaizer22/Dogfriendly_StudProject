package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserDetailFragment


class UserDetailActivity : BaseActivity() {

    private var userId: String? = null

    companion object{

        private const val INTENT_EXTRA_PARAM_USER_ID = "INTENT_PARAM_USER_ID"

        fun getCallingIntent(context: Context, userId: String?): Intent {
            val callingIntent = Intent(context, UserDetailActivity::class.java)
            callingIntent.putExtra(INTENT_EXTRA_PARAM_USER_ID, userId)
            return callingIntent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
    }

    //РАСКОМЕНТИТЬ СТРОЧКУ!!! ID ВВЕДЕН ДЛЯ ТЕСТА!!!
    override fun initializeActivity(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            userId = intent.extras?.getString(INTENT_EXTRA_PARAM_USER_ID)
//            addFragment(R.id.ft_container, UserDetailFragment(userId))
            addFragment(R.id.ft_container, UserDetailFragment("-MEYGzlqgcVxSHRV5LQ9"))


        }
    }

}