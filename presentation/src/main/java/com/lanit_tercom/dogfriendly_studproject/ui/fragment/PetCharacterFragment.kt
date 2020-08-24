package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.card.MaterialCardView
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.PetDetailEditView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.BaseActivity
import com.lanit_tercom.dogfriendly_studproject.ui.activity.UserDetailActivity

class PetCharacterFragment(private val userId: String?): BaseFragment(), PetDetailEditView{
    private val selected: ArrayList<String> = ArrayList()
    private lateinit var elements: ArrayList<MaterialCardView>
    private lateinit var pet: PetModel

    fun initializePet(pet: PetModel){
        this.pet = pet
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pet_character, container, false)
        initialize(view)

        selected.clear()

        view.findViewById<ImageView>(R.id.back_button).setOnClickListener { activity?.onBackPressed() }
        //Выбрали элементы, передали список элементов модельке питомца и пошли дальше в фото
        view.findViewById<Button>(R.id.ready_button).setOnClickListener {
            pet.character = selected
            navigateToNext(pet)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        selected.clear()
    }

    //Рудимент от которого не избавится, если наследоваться от BaseFragment()
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
        output.add(attachOnClick(view.findViewById(R.id.kind), "naturalist"))
        output.add(attachOnClick(view.findViewById(R.id.proud), "sqirell_hater"))
        output.add(attachOnClick(view.findViewById(R.id.coward), "searcher"))

        output.add(attachOnClick(view.findViewById(R.id.aggressive), "gourmet"))
        output.add(attachOnClick(view.findViewById(R.id.squirell_hater), "cat_lover"))
        output.add(attachOnClick(view.findViewById(R.id.tomboy), "shy"))
        output.add(attachOnClick(view.findViewById(R.id.funny), "swimmer"))

        output.add(attachOnClick(view.findViewById(R.id.fighnting), "sherlock"))
        output.add(attachOnClick(view.findViewById(R.id.sporty), "fighter"))
        output.add(attachOnClick(view.findViewById(R.id.restless), "fidget"))
        output.add(attachOnClick(view.findViewById(R.id.shy), "angel"))

        return output
    }

    override fun navigateToNext(pet: PetModel) {
        (activity as UserDetailActivity).startPetPhotoEdit(pet)
    }

    override fun showError(message: String) {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

}