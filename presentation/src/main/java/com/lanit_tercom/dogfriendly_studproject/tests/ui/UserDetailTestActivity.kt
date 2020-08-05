package com.lanit_tercom.dogfriendly_studproject.tests.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lanit_tercom.dogfriendly_studproject.R

/**
 * Клавиатура портит разметку, в остальном все вроде более менее норм.
 * Фотка теперь берется из галереи с помощью image-cropper и обрезается в круг c помощбю glide
 */
class UserDetailTestActivity : AppCompatActivity() {
    //Декларация UI элементов и переменных
    private lateinit var btnToUserDetailEdit: ImageButton
    private lateinit var btnAddPet: ImageButton
    private lateinit var btnToMap: ImageButton
    private lateinit var btnToChats: ImageButton
    private lateinit var btnToSettings: ImageButton
    private lateinit var petList: ListView
    private lateinit var nameTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var avatar: ImageView
    private var avatarUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_detail)

        //Инициализация UI элементов, присвоение onClickListener'ов
        nameTextView = findViewById(R.id.name)
        ageTextView = findViewById(R.id.age)
        avatar = findViewById(R.id.user_avatar)
        petList = findViewById(R.id.pet_list)

        btnToUserDetailEdit = findViewById(R.id.edit_button)
        btnToUserDetailEdit.setOnClickListener { toUserEdit() }

        btnAddPet = findViewById(R.id.add_pet_button)
        btnAddPet.setOnClickListener { addPet() }

        btnToMap = findViewById(R.id.to_map_button)
        btnToMap.setOnClickListener { toMap() }

        btnToChats = findViewById(R.id.to_chats_button)
        btnToChats.setOnClickListener { toChats() }

        btnToSettings = findViewById(R.id.to_settings_button)
        btnToSettings.setOnClickListener { toSettings() }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Динамическое задание размера блоку "о себе"
        val appbar = findViewById<View>(R.id.appbar) as AppBarLayout
        val bottomNav = findViewById<View>(R.id.bottom_nav) as BottomNavigationView
        val heightDp = resources.displayMetrics.heightPixels * 0.5 - 10 * resources.displayMetrics.density
        val lp = appbar.layoutParams as CoordinatorLayout.LayoutParams
        lp.height = heightDp.toInt()


        //Открытие/скрытие нижней панели
        appbar.addOnOffsetChangedListener(object : UserDetailTestActivity.AppBarStateChangeListener() {

            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                if (state == State.EXPANDED)
                    bottomNav.visibility = View.GONE
                if (state == State.COLLAPSED)
                    bottomNav.visibility = View.VISIBLE
            }

        })
    }


    //Методы для навигации и взаимодействия с другими экранами
    private fun toPetDetail() {
        val intent: Intent = Intent(this, PetDetailTestActivity::class.java)
        startActivity(intent)
    }

    //todo
    private fun toMap() {}

    //todo
    private fun toChats() {}

    //todo
    private fun toSettings() {}


    private fun toUserEdit() {
        val intent: Intent = Intent(this, UserDetailEditTestActivity::class.java)
        startActivityForResult(intent, 1)
    }

    private fun addPet() {
        val intent: Intent = Intent(this, PetDetailEditTestActivity::class.java)
        startActivity(intent)
    }


    //Обратная связь с UserDetailEdit(Test)Activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                nameTextView.text = data?.getStringExtra("name")
                ageTextView.text = data?.getStringExtra("age")
                val a: String? = data?.getStringExtra("avatarId")
                avatarUri = Uri.parse(data?.getStringExtra("avatarUri"))
                if (avatarUri != null)
                    Glide.with(this)
                            .load(avatarUri)
                            .circleCrop()
                            .into(avatar);
                else
                    avatar.setImageResource(R.drawable.ic_set_avatar_green)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }


    //Класс для реализации открытия/скрытия нижней панели
    abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {
        enum class State {
            EXPANDED, COLLAPSED, IDLE
        }

        private var mCurrentState = State.IDLE
        override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
            mCurrentState = if (i == 0) {
                if (mCurrentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED)
                }
                State.EXPANDED
            } else if (Math.abs(i) >= appBarLayout.totalScrollRange) {
                if (mCurrentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED)
                }
                State.COLLAPSED
            } else {
                if (mCurrentState != State.IDLE) {
                    onStateChanged(appBarLayout, State.IDLE)
                }
                State.IDLE
            }
        }

        abstract fun onStateChanged(appBarLayout: AppBarLayout?, state: State?)
    }

}