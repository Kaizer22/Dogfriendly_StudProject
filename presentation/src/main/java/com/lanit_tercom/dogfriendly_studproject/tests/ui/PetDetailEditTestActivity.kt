package com.lanit_tercom.dogfriendly_studproject.tests.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.lanit_tercom.dogfriendly_studproject.R

/**
 * Где взять кнопки муж/жен в невыделенном варианте? Чтобы только после нажатия выделялось зеленым.
 */
class PetDetailEditTestActivity : AppCompatActivity() {
    private lateinit var backButton: ImageButton
    private lateinit var readyButton: Button
    private lateinit var editPetName: TextInputEditText
    private lateinit var editPetBreed: TextInputEditText
    private lateinit var editPetAge: TextInputEditText
    private lateinit var avatar: ImageView
    private lateinit var radioGroup: RadioGroup
    private var avatarUri: Uri? = null
    private val PICK_IMAGE = 100
    private var sex: String? = null
    private lateinit var data: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pet_detail_edit)

        data = Intent(this, PetCharacterEditTestActivity::class.java)

        editPetName = findViewById(R.id.edit_pet_name)
        editPetBreed = findViewById(R.id.edit_pet_breed)
        editPetAge = findViewById(R.id.edit_pet_age)

        backButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }


        readyButton = findViewById(R.id.ready_button)
        readyButton.setOnClickListener {
            data.putExtra("name", editPetName.text.toString())
            data.putExtra("age", editPetAge.text.toString())
            data.putExtra("breed", editPetBreed.text.toString())
            data.putExtra("sex", sex)
            data.putExtra("avatarUri", avatarUri.toString())
            startActivity(data)
        }

        avatar = findViewById(R.id.pet_avatar)
        avatar.setOnClickListener { openGallery() }

        radioGroup = findViewById(R.id.sex_radio_group)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.man_radio_button -> sex = "men"
                R.id.woman_radio_button -> sex = "woman"
            }
        }


    }

    //Открывает галлерею
    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }

    //Обратная связь с галлереей
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            val imageUri: Uri? =  data?.data
            avatarUri = imageUri
            avatar.setImageURI(imageUri)
        }

    }




}