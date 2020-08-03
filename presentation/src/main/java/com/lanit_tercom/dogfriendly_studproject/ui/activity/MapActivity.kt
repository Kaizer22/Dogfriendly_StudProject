package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.MapFragment

/**
 * Активность карты.
 * Запускает фрагмент с картой.
 * @author prostak.sasha111@mail.ru
 */
class MapActivity : BaseActivity() {

    companion object{

        fun getCallingIntent(context: Context): Intent =
            Intent(context, MapActivity::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
    }

    fun navigateToUserDetail(userId: String?) =
        navigator?.navigateToUserDetail(this, userId)


    override fun initializeActivity(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            addFragment(R.id.ft_container, MapFragment())
        }
    }

    override fun onBackPressed() { return }

}