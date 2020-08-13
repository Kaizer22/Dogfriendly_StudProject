package com.lanit_tercom.dogfriendly_studproject.ui.activity.pet_detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.lanit_tercom.dogfriendly_studproject.R
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlin.collections.ArrayList


class PetPhotoActivity : AppCompatActivity() {
    private lateinit var elements: ArrayList<Pair<ImageView, ImageView>>
    private lateinit var backButton: ImageButton
    private lateinit var readyButton: Button
    private lateinit var loadPhotoButton: ImageView
    private lateinit var data: Intent
    private var nextImageSpace: Int = 0
    private var photos: Array<String> = Array(8) {"0"}
    private val emptyPhoto = Uri.parse("android.resource://com.lanit_tercom.dogfriendly_studproject.ui.activity.pet_detail/" + R.drawable.ic_button_add_photo)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pet_photo)
        elements = initialize()
        loadPhotoButton = findViewById(R.id.photo_image)
        loadPhotoButton.setOnClickListener {
            if (nextImageSpace != 8)
                loadPhoto()
        }

        data= Intent()
        if(intent.extras != null){
            data.putExtras(intent.extras!!)
        }



        backButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }


        readyButton = findViewById(R.id.ready_button)
        readyButton.setOnClickListener {
            val photoList = ArrayList<String>()
            for(photo in photos) photoList.add(photo)
            data.putStringArrayListExtra("photo",  photoList)
            setResult(Activity.RESULT_OK, data)
            finish()
        }

    }

    //Инициализация элементов, присвоение им OnClickListener
    private fun initialize() : ArrayList<Pair<ImageView, ImageView>>{
        val output = ArrayList<Pair<ImageView,ImageView>>()
        output.add(Pair(findViewById(R.id.photo1),findViewById(R.id.remove_photo1)))
        output.add(Pair(findViewById(R.id.photo2),findViewById(R.id.remove_photo2)))
        output.add(Pair(findViewById(R.id.photo3),findViewById(R.id.remove_photo3)))
        output.add(Pair(findViewById(R.id.photo4),findViewById(R.id.remove_photo4)))
        output.add(Pair(findViewById(R.id.photo5),findViewById(R.id.remove_photo5)))
        output.add(Pair(findViewById(R.id.photo6),findViewById(R.id.remove_photo6)))
        output.add(Pair(findViewById(R.id.photo7),findViewById(R.id.remove_photo7)))
        output.add(Pair(findViewById(R.id.photo8),findViewById(R.id.remove_photo8)))

        for(i in 0..6) output[i].second.setOnClickListener{deleteMiddle(i)}
        output[7].second.setOnClickListener {deleteLast()}

        for(element in output) element.second.visibility = View.INVISIBLE

        return output
    }

    //Удалить элемент не находящийся в конце
    private fun deleteMiddle(i: Int){
        for(j in (i..6)){
            elements[j].first.setImageDrawable(elements[j+1].first.drawable)
            photos[i]=photos[i+1]
        }
        elements[7].first.setImageURI(emptyPhoto)
        nextImageSpace--
        elements[nextImageSpace].second.visibility = View.INVISIBLE
    }

    //Удалить последний элемент
    private fun deleteLast(){
        photos[7] = "0"
        elements[7].first.setImageURI(emptyPhoto)
        elements[7].second.visibility = View.INVISIBLE
        nextImageSpace--
        
    }

    //Вставить фото в элемент
    private fun setPhoto(elements: ArrayList<Pair<ImageView, ImageView>>, position: Int, image: Uri){
        elements[position].first.setImageURI(image)
        elements[position].second.visibility = View.VISIBLE
    }

    //Загрузка/создание/обрезание аватара
    private fun loadPhoto() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(1,1)
                .setActivityTitle("")
                .start(this)
    }

    //Обратная связь с галереей
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                if(nextImageSpace != 8){
                    photos[nextImageSpace] = result.toString()
                    setPhoto(elements, nextImageSpace++, resultUri)

                }
            }
//            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) { }
        }
    }

}