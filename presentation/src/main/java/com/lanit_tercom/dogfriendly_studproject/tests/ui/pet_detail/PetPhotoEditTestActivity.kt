package com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lanit_tercom.dogfriendly_studproject.R
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView


/**
 * Пока данные с полученными фото никуда не передаются
 * Установка размеров через ItemDecoration работает кривовато
 */
class PetPhotoEditTestActivity : AppCompatActivity(), PhotoAdapter.OnPhotoListener{
    private lateinit var data: Intent
    private lateinit var backButton: ImageButton
    private lateinit var readyButton: Button
    private lateinit var photoAdapter: PhotoAdapter
    private var nextImageSpace: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pet_photo_edit)

        val images = initializeImages()

        val photoList = findViewById<RecyclerView>(R.id.photo_elements)
        photoAdapter = PhotoAdapter(images, this)
        val gridLayoutManager = GridLayoutManager(this, 3)
        photoList.layoutManager = gridLayoutManager
        photoList.itemAnimator = DefaultItemAnimator()
        photoList.addItemDecoration(GridSpacingItemDecorator(3, resources.getDimensionPixelSize(R.dimen.recycler_view_item_width), true, 12))

        photoList.adapter = photoAdapter

        data = Intent(this, PetDetailTestActivity::class.java)
        data.putExtras(intent)

        backButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        readyButton = findViewById(R.id.ready_button)
        readyButton.setOnClickListener {
            startActivity(data)
        }

    }

    fun initializeImages(): ArrayList<Uri> {
        val images = ArrayList<Uri>()
        val addPhotoImage = Uri.parse("android.resource://com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail/" + R.drawable.ic_button_add_photo)
        images.add(addPhotoImage)
        images.add(addPhotoImage)
        images.add(addPhotoImage)
        images.add(addPhotoImage)
        images.add(addPhotoImage)
        images.add(addPhotoImage)
        images.add(addPhotoImage)
        images.add(addPhotoImage)
        images.add(addPhotoImage)
        return images
    }

    override fun onPhotoClick(position: Int) {

        if(nextImageSpace != 9){
            loadPhoto()
        }
    }


    private fun loadPhoto() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(1,1)
                .setActivityTitle("")
                .start(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                photoAdapter.images[nextImageSpace]= resultUri
                photoAdapter.notifyDataSetChanged()
                nextImageSpace++
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }

    }
}

