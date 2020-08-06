package com.lanit_tercom.dogfriendly_studproject.tests.ui.user_detail


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.lanit_tercom.dogfriendly_studproject.R
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView



class UserDetailEditTestActivity : AppCompatActivity() {
    //Декларация UI элементов и переменных
    private lateinit var backButton: ImageButton
    private lateinit var readyButton: Button
    private lateinit var editName: TextInputEditText
    private lateinit var editAge: TextInputEditText
    private lateinit var avatar: ImageView
    private var avatarUri: Uri? = null
    private val PICK_IMAGE = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_detail_edit)

        //Инициализации UI элементов, присвоение onClickListener'ов
        editName = findViewById(R.id.edit_name)
        editAge = findViewById(R.id.edit_age)

        avatar = findViewById(R.id.user_avatar)
        avatar.setOnClickListener { loadAvatar() }

        backButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        readyButton = findViewById(R.id.ready_button)
        readyButton.setOnClickListener {
            setResult(Activity.RESULT_OK)
            val data = Intent()
            data.putExtra("name", editName.text.toString())
            data.putExtra("age", editAge.text.toString())
            data.putExtra("avatarUri", avatarUri.toString())
            setResult(Activity.RESULT_OK, data)
            finish()
        }

    }

    //Загрузка/создание/обрезание аватара
    private fun loadAvatar() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAspectRatio(1,1)
                .setRequestedSize(320, 320)
                .setActivityTitle("")
                .start(this);
    }

    //Обратная связь с галлереей
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                avatarUri = resultUri

                Glide.with(this)
                .load(avatarUri)
                .circleCrop()
                .into(avatar);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }

    }


}