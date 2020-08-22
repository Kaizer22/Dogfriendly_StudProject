package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.*


class UserDetailActivity : BaseActivity() {
    private var userId: String? = null
    private lateinit var userDetailFragment: UserDetailFragment
    private lateinit var userDetailEditFragment: UserDetailEditFragment
    private lateinit var petDetailEditFragment: PetDetailEditFragment
    private lateinit var petCharacterFragment: PetCharacterFragment
    private lateinit var petPhotoFragment: PetPhotoFragment

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

    //Перейти на экран пользователя
    fun startUserDetail(){
        Log.i("TEST_ACTIVITY", "STARTING_USER_DETAIL")
        if(userDetailFragment.isAdded){
            replaceFragment(R.id.ft_container, userDetailFragment)
        } else {
            addFragment(R.id.ft_container, userDetailFragment)
        }
    }

    //Перейти на экран редактирования пользователя
    fun startUserDetailEdit(){
        Log.i("TEST_ACTIVITY", "STARTING_USER_DETAIL_EDIT")
        if(userDetailEditFragment.isAdded){
            replaceFragment(R.id.ft_container, userDetailEditFragment)
        } else {
            addFragment(R.id.ft_container, userDetailEditFragment)
        }
    }

    fun startPetDetailEdit(pet: PetModel){
        Log.i("TEST_ACTIVITY", "STARTING_PET_DETAIL_EDIT")
        petDetailEditFragment.initializePet(pet)
        if(petDetailEditFragment.isAdded){
            replaceFragment(R.id.ft_container, petDetailEditFragment)
        } else {
            addFragment(R.id.ft_container, petDetailEditFragment)
        }
    }

    fun startPetCharacterEdit(pet: PetModel){
        Log.i("TEST_ACTIVITY", "STARTING_PET_CHARACTER_EDIT")
        petCharacterFragment.initializePet(pet)
        if(petCharacterFragment.isAdded){
            replaceFragment(R.id.ft_container, petCharacterFragment)
        } else {
            addFragment(R.id.ft_container, petCharacterFragment)
        }
    }

    fun startPetPhotoEdit(pet: PetModel){
        Log.i("TEST_ACTIVITY", "STARTING_PET_PHOTO_EDIT")
        petPhotoFragment.initializePet(pet)
        if(petPhotoFragment.isAdded){
            replaceFragment(R.id.ft_container, petPhotoFragment)
        } else {
            addFragment(R.id.ft_container, petPhotoFragment)
        }
    }

    //РАСКОМЕНТИТЬ СТРОЧКУ!!! ID ВВЕДЕН ДЛЯ ТЕСТА!!!
    override fun initializeActivity(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            userId = "-MEYGzlqgcVxSHRV5LQ9"
//            userId = intent.extras?.getString(INTENT_EXTRA_PARAM_USER_ID)
            userDetailFragment = UserDetailFragment(userId)
            userDetailEditFragment = UserDetailEditFragment(userId)
            petDetailEditFragment = PetDetailEditFragment(userId)
            petCharacterFragment = PetCharacterFragment(userId)
            petPhotoFragment = PetPhotoFragment(userId)
            startUserDetail()

        }
    }

}