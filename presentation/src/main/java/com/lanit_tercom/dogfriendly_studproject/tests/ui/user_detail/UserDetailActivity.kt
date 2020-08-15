package com.lanit_tercom.dogfriendly_studproject.tests.ui.user_detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.tests.ui.EditTextActivity
import com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail.PetDetailEditActivity


class UserDetailActivity : AppCompatActivity() {
    //Декларация UI элементов и переменных
    private lateinit var btnToUserDetailEdit: ImageButton
    private lateinit var btnAddPet: MaterialButton
    private lateinit var btnToMap: ImageButton
    private lateinit var btnToChats: ImageButton
    private lateinit var btnToSettings: ImageButton
    private lateinit var petList: RecyclerView
    private lateinit var nameTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var plansText: TextView
    private lateinit var aboutText: TextView
    private lateinit var avatar: ImageView
    private lateinit var petListAdapter: PetListAdapter
    private var pets: ArrayList<PetListItem> = ArrayList()
    private var avatarUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_detail)

        //Инициализация UI элементов, присвоение onClickListener'ов
        nameTextView = findViewById(R.id.name)
        ageTextView = findViewById(R.id.age)
        avatar = findViewById(R.id.user_avatar)

        petList = findViewById(R.id.pet_list)
        petList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        petListAdapter = PetListAdapter(pets)
        petList.adapter = petListAdapter

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

        plansText = findViewById(R.id.plans_text)
        plansText.setOnClickListener {
            val toEditPlanText = Intent(this, EditTextActivity::class.java)
            toEditPlanText.putExtra("editText", "plans")
            toEditPlanText.putExtra("title", "Планы на прогулку")
            startActivityForResult(toEditPlanText, 2)

        }

        aboutText = findViewById(R.id.about_text)
        aboutText.setOnClickListener {
            val toEditAboutText = Intent(this, EditTextActivity::class.java)
            toEditAboutText.putExtra("editText", "about")
            toEditAboutText.putExtra("title", "О себе")
            startActivityForResult(toEditAboutText, 3)
        }

        //Чтобы клавиатура не открывалась там, где не надо/убиралась там где надо
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        //Динамическое задание размера блоку "о себе"
        val appbar = findViewById<View>(R.id.appbar) as AppBarLayout
        val bottomNav = findViewById<View>(R.id.bottom_nav) as BottomNavigationView
        val heightDp = resources.displayMetrics.heightPixels * 0.5 - 10 * resources.displayMetrics.density
        val lp = appbar.layoutParams as CoordinatorLayout.LayoutParams
        lp.height = heightDp.toInt()


        //Открытие/скрытие нижней панели
        appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {

            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                if (state == State.EXPANDED)
                    bottomNav.visibility = View.GONE
                if (state == State.COLLAPSED)
                    bottomNav.visibility = View.VISIBLE
            }

        })
    }


    //todo
    private fun toPetDetail() {}

    //todo
    private fun toMap() {}

    //todo
    private fun toChats() {}

    //todo
    private fun toSettings() {}


    private fun toUserEdit() {
        val intent: Intent = Intent(this, UserDetailEditActivity::class.java)
        startActivityForResult(intent, 1)
    }

    private fun addPet() {
        val intent: Intent = Intent(this, PetDetailEditActivity::class.java)
        startActivityForResult(intent, 4)
    }


    //Обратная связь с другими Activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Загрузка аватара юзера
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
                            .into(avatar)
                else
                    avatar.setImageResource(R.drawable.ic_set_avatar_green)
            }
        }
        //Информация о прогулке из EditTextActivity
        if(requestCode == 2){
            if(resultCode == Activity.RESULT_OK){
                plansText.text = data?.getStringExtra("output")
            }
        }
        //Информация о себе из EditTextActivity
        if(requestCode == 3){
            if(resultCode == Activity.RESULT_OK){
                aboutText.text = data?.getStringExtra("output")
            }
        }
        //Информация получаемая в процессе создания профиля собаки (для создания ссылки на этот профиль)
        if (requestCode == 4) {
            if (resultCode == Activity.RESULT_OK) {
                val avatarUriString: String? = data?.getStringExtra("avatarUri")
                val name: String = data?.getStringExtra("name") ?: "1"
                val breed: String = data?.getStringExtra("breed") ?: "2"
                val age: String = data?.getStringExtra("age") ?: "3"
                val desc: String = "$breed, $age лет"
                pets.add(PetListItem(avatarUriString, name, desc))
                petListAdapter.notifyDataSetChanged()
            }
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

    //Класс представляющий список питомцев юзера
    data class PetListItem(val uri: String?, val name: String?, val desc: String?)

}