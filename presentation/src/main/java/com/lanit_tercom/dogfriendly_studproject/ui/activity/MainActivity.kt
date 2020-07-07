package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.os.Bundle
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserDetailFragment

class MainActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Тут должен запускаться фрагмент с картой, но он еще не готов
        addFragment(R.id.activity_main, UserDetailFragment())
    }
}
