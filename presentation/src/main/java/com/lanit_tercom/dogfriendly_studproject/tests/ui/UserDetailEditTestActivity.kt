package com.lanit_tercom.dogfriendly_studproject.tests.ui


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.lanit_tercom.dogfriendly_studproject.R

/**
 * Тоже самое что и в UserDetail - надо как то поставить большую картинку в круг
 */
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
        editName =  findViewById(R.id.edit_name)
        editAge = findViewById(R.id.edit_age)

        avatar = findViewById(R.id.user_avatar)
        avatar.setOnClickListener { openGallery() }

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