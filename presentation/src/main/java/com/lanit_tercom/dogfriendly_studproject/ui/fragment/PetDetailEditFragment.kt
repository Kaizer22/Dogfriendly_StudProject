package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.ui.activity.BaseActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class PetDetailEditFragment(private val userId: String?): BaseFragment() {
    private lateinit var editPetName: TextInputEditText
    private lateinit var editPetBreed: TextInputEditText
    private lateinit var editPetAge: TextInputEditText
    private lateinit var avatar: ImageView
    private lateinit var menButton: MaterialButton
    private lateinit var womanButton: MaterialButton
    private var avatarUri: Uri? = null
    private var gender: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.fragment_pet_detail_edit, container, false)

        editPetName = view.findViewById(R.id.edit_pet_name)
        editPetBreed = view.findViewById(R.id.edit_pet_breed)
        editPetAge = view.findViewById(R.id.edit_pet_age)

        avatar = view.findViewById(R.id.pet_avatar)
        avatar.setOnClickListener { loadAvatar() }

        view.findViewById<ImageView>(R.id.back_button).setOnClickListener { activity?.onBackPressed() }

        //Присвоили модельке питомца что надо и пошли дальше в характеры
        view.findViewById<Button>(R.id.ready_button).setOnClickListener {
            val pet = PetModel()
            pet.name = editPetName.text.toString()
            pet.age = editPetAge.text.toString().toInt()
            pet.breed = editPetBreed.text.toString()
            pet.gender = gender
            pet.avatar = avatarUri
            (activity as BaseActivity).replaceFragment(R.id.ft_container, PetCharacterFragment(userId, pet))
        }

        womanButton = view.findViewById(R.id.woman_button)
        womanButton.setOnClickListener{ setGender("woman")}

        menButton = view.findViewById(R.id.men_button)
        menButton.setOnClickListener { setGender("men") }

        return view
    }

    //Рудимент от которого не избавится, если наследоваться от BaseFragment()
    override fun initializePresenter() {}

    //Обработка нажатий кнопо выбора пола
    private fun setGender(gender: String){
        if(gender == "men"){
            menButton.background.setTint(Color.parseColor("#B2BC24"))
            menButton.setTextColor(Color.parseColor("#FFFFFF"))
            womanButton.background.setTint(Color.parseColor("#FEFFF0"))
            womanButton.setTextColor(Color.parseColor("#94A604"))
            this.gender = "men"
        } else {
            womanButton.background.setTint(Color.parseColor("#B2BC24"))
            womanButton.setTextColor(Color.parseColor("#FFFFFF"))
            menButton.background.setTint(Color.parseColor("#FEFFF0"))
            menButton.setTextColor(Color.parseColor("#94A604"))
            this.gender = "woman"
        }
    }

    //Загрузка/создание/обрезание аватара
    private fun loadAvatar() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(1,1)
                .setActivityTitle("")
                .start(context!!, this)
    }

    //Обратная связь с галереей, проброс данных обратно в UserDetailActivity(4)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                avatarUri = resultUri
                avatar.setImageURI(avatarUri)
            }
//            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {}
        }

    }


}