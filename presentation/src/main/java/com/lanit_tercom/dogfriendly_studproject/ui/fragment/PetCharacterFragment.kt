package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.material.card.MaterialCardView
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.ui.activity.BaseActivity

class PetCharacterFragment(private val userId: String?, private val pet: PetModel): BaseFragment() {
    private val selected: ArrayList<String> = ArrayList()
    private lateinit var elements: ArrayList<MaterialCardView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pet_character, container, false)
        initialize(view)

        view.findViewById<ImageView>(R.id.back_button).setOnClickListener { activity?.onBackPressed() }

        view.findViewById<Button>(R.id.ready_button).setOnClickListener {
            pet.character = selected
            (activity as BaseActivity).replaceFragment(R.id.ft_container, PetPhotoFragment(userId, pet))
        }

        return view
    }

    override fun initializePresenter() {}

    //Функция присваивающая onClickListener передаваемому CardView
    private fun attachOnClick(characterView: MaterialCardView, characterName: String): MaterialCardView {
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
    private fun initialize(view: View): ArrayList<MaterialCardView>{
        val output: ArrayList<MaterialCardView> = ArrayList()

        output.add(attachOnClick(view.findViewById(R.id.active), "active"))
        output.add(attachOnClick(view.findViewById(R.id.kind), "kind"))
        output.add(attachOnClick(view.findViewById(R.id.proud), "proud"))
        output.add(attachOnClick(view.findViewById(R.id.coward), "coward"))

        output.add(attachOnClick(view.findViewById(R.id.aggressive), "aggressive"))
        output.add(attachOnClick(view.findViewById(R.id.squirell_hater), "squirell_hater"))
        output.add(attachOnClick(view.findViewById(R.id.tomboy), "tomboy"))
        output.add(attachOnClick(view.findViewById(R.id.funny), "funny"))

        output.add(attachOnClick(view.findViewById(R.id.fighnting), "fighting"))
        output.add(attachOnClick(view.findViewById(R.id.sporty), "sporty"))
        output.add(attachOnClick(view.findViewById(R.id.restless), "restless"))
        output.add(attachOnClick(view.findViewById(R.id.shy), "shy"))

        return output
    }

}