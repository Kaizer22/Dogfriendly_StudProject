
package com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.google.android.material.card.MaterialCardView
import com.lanit_tercom.dogfriendly_studproject.R
import com.theartofdev.edmodo.cropper.CropImage

class PetCharacterActivity : AppCompatActivity() {
    private val selected: ArrayList<String> = ArrayList()
    private lateinit var elements: ArrayList<MaterialCardView>
    private lateinit var backButton: ImageButton
    private lateinit var readyButton: Button
    private lateinit var toPetPhoto: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pet_character)
        initialize()

        toPetPhoto = Intent(this, PetPhotoActivity::class.java)
        if(intent.extras != null){
            toPetPhoto.putExtras(intent.extras!!)
        }


        backButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }


        readyButton = findViewById(R.id.ready_button)
        readyButton.setOnClickListener {
            toPetPhoto.putStringArrayListExtra("character", selected)

            startActivityForResult(toPetPhoto, 4)
        }

    }

    //Функция присваивающая onClickListener передаваемому CardView
    private fun attachOnClick(characterView: MaterialCardView, characterName: String): MaterialCardView{
        characterView.setOnClickListener {
            if(!selected.contains(characterName)){
                selected.add(characterName)
                characterView.strokeWidth = 5
            } else {
                selected.remove(characterName)
                characterView.strokeWidth = 0
            }
        }
        return characterView
    }

    //Инициализация элементов экрана
    private fun initialize(): ArrayList<MaterialCardView>{
        val output: ArrayList<MaterialCardView> = ArrayList()

        output.add(attachOnClick(findViewById(R.id.active), "active"))
        output.add(attachOnClick(findViewById(R.id.kind), "kind"))
        output.add(attachOnClick(findViewById(R.id.proud), "proud"))
        output.add(attachOnClick(findViewById(R.id.coward), "coward"))

        output.add(attachOnClick(findViewById(R.id.aggressive), "aggressive"))
        output.add(attachOnClick(findViewById(R.id.squirell_hater), "squirell_hater"))
        output.add(attachOnClick(findViewById(R.id.tomboy), "tomboy"))
        output.add(attachOnClick(findViewById(R.id.funny), "funny"))

        output.add(attachOnClick(findViewById(R.id.fighnting), "fighting"))
        output.add(attachOnClick(findViewById(R.id.sporty), "sporty"))
        output.add(attachOnClick(findViewById(R.id.restless), "restless"))
        output.add(attachOnClick(findViewById(R.id.shy), "shy"))

        return output
    }

    //Проброска данных о питомце обратно в UserDetailActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 4){
            setResult(Activity.RESULT_OK, data)
            finish()
        }

    }
}