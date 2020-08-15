package com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.lanit_tercom.dogfriendly_studproject.R
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView


class PetDetailEditActivity : AppCompatActivity() {
    private lateinit var backButton: ImageButton
    private lateinit var readyButton: Button
    private lateinit var editPetName: TextInputEditText
    private lateinit var editPetBreed: TextInputEditText
    private lateinit var editPetAge: TextInputEditText
    private lateinit var avatar: ImageView
    private lateinit var menButton: MaterialButton
    private lateinit var womanButton: MaterialButton
    private var avatarUri: Uri? = null
    private var gender: String? = null
    private lateinit var toPetCharacter: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pet_detail_edit)

        toPetCharacter = Intent(this, PetCharacterActivity::class.java)

        editPetName = findViewById(R.id.edit_pet_name)
        editPetBreed = findViewById(R.id.edit_pet_breed)
        editPetAge = findViewById(R.id.edit_pet_age)

        backButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }


        readyButton = findViewById(R.id.ready_button)
        readyButton.setOnClickListener {
            toPetCharacter.putExtra("name", editPetName.text.toString())
            toPetCharacter.putExtra("age", editPetAge.text.toString())
            toPetCharacter.putExtra("breed", editPetBreed.text.toString())
            toPetCharacter.putExtra("gender", gender)
            toPetCharacter.putExtra("avatarUri", avatarUri.toString())
            startActivityForResult(toPetCharacter, 4)
        }

        avatar = findViewById(R.id.pet_avatar)
        avatar.setOnClickListener { loadAvatar() }

        menButton = findViewById(R.id.men_button)
        menButton.setOnClickListener{ setGender("men")}
        womanButton = findViewById(R.id.woman_button)
        womanButton.setOnClickListener { setGender("woman") }

    }

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
                .start(this)
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
        if(requestCode == 4){
            setResult(Activity.RESULT_OK, data)
            finish()
        }

    }

}