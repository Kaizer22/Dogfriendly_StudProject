package com.lanit_tercom.dogfriendly_studproject.tests.ui


import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.tests.ui.user_detail.PetListAdapter
import com.lanit_tercom.dogfriendly_studproject.tests.ui.user_detail.UserDetailActivity
import kotlinx.android.synthetic.main.user_detail_edit.view.*


/**
 * Запускается окно юзера
 */
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, UserTestActivity::class.java))

    }

}





