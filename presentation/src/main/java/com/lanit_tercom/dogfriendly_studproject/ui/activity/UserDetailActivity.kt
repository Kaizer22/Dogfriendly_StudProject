package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserDetailFragment

/**
 * Активность данных о пользователе.
 * Запускает фрагмент с данными о пользователе.
 * @author prostak.sasha111@mail.ru
 */
class UserDetailActivity : BaseActivity() {

    private var userId: Int? = null

    companion object{

        private const val INTENT_EXTRA_PARAM_USER_ID = "INTENT_PARAM_USER_ID"

        fun getCallingIntent(context: Context, userId: Int?): Intent {
            val callingIntent = Intent(context, UserDetailActivity::class.java)
            callingIntent.putExtra(INTENT_EXTRA_PARAM_USER_ID, userId)
            return callingIntent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
    }

    override fun initializeActivity(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            userId = intent.extras?.getInt(INTENT_EXTRA_PARAM_USER_ID)
            addFragment(R.id.ft_container, UserDetailFragment(userId))
        }
    }

}
