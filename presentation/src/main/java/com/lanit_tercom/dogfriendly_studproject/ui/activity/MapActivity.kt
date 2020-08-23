package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.tests.ui.map.MapSettingsActivity
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.MapFragment
import kotlinx.android.synthetic.main.activity_map.*

/**
 * Активность карты.
 * Запускает фрагмент с картой.
 * @author prostak.sasha111@mail.ru
 */
class MapActivity : BaseActivity(), View.OnClickListener {

    companion object{

        fun getCallingIntent(context: Context): Intent =
            Intent(context, MapActivity::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        button_map_settings.setOnClickListener(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_map, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun navigateToUserDetail(userId: String?) =
        navigator?.navigateToUserDetail(this, userId)


    override fun initializeActivity(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            addFragment(R.id.ft_container, MapFragment())
        }
    }

    override fun onBackPressed() { return }
    override fun onClick(v: View?) {
        when (v?.id){
            R.id.button_map_settings -> {
                startActivity(Intent(this, MapSettingsActivity::class.java))
            }
        }
    }
}