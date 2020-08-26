package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.PetDetailView
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.Character
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.CharacterAdapter
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.PetListAdapter
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.PhotoAdapter

class PetDetailObserverFragment(private val pet: PetModel) : BaseFragment(), PetDetailView{
    private lateinit var avatar: ImageView
    private lateinit var name: TextView
    private lateinit var description: TextView
//    private lateinit var fab: FloatingActionButton
    private lateinit var aboutText: TextView
    private lateinit var characterList: RecyclerView
    private lateinit var photoList: RecyclerView

    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var photoAdapter: PhotoAdapter



    private fun ageDesc(age: Int?): String{
        if(age == null) return ""
        val ageLastNumber = age % 10
        val exclusion = age % 100 in 11..14
        var old = ""

        if (ageLastNumber == 1) old = "год"
        else if (ageLastNumber == 0 || ageLastNumber in 5..9) old = "лет"
        else if (ageLastNumber in 2..4) old = "года"
        if (exclusion) old = "лет"
        return "$age $old"
    }

    fun initialize(pet: PetModel){
        if(pet.name != null)
            name.text = pet.name

        var desc = ""
        if(pet.breed != null) desc += pet.breed
        if(pet.age != null) desc += ", "+ ageDesc(pet.age)
        if(desc.startsWith(", ")) {
            description.text = desc.substring(2)
        } else {
            description.text = desc
        }

        if(pet.avatar != null){
            Glide.with(this)
                    .load(pet.avatar)
                    .into(avatar)
        }

        if(pet.about != null){
            aboutText.text = pet.about
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pet_detail_new, container, false)

        avatar = view.findViewById(R.id.pet_avatar)
        name = view.findViewById(R.id.name)
        description = view.findViewById(R.id.description)
        aboutText = view.findViewById(R.id.about_text)

        characterList = view.findViewById(R.id.character_list)
        photoList = view.findViewById(R.id.photo_list)


        val characters = ArrayList<Character>()
        if(pet.character != null){
            for(character in pet.character!!){
                when(character){

                    "active" -> characters.add(Character("Активная", R.drawable.active))
                    "naturalist" -> characters.add(Character("Натуралист", R.drawable.naturalist))
                    "sqirell_hater" -> characters.add(Character("Гроза белок", R.drawable.sqirell_hater))
                    "searcher" -> characters.add(Character("Ищет клады", R.drawable.searcher))

                    "gourmet" -> characters.add(Character("Гурман", R.drawable.gourmet))
                    "cat_lover" -> characters.add(Character("Кошатник", R.drawable.cat_lover))
                    "shy" -> characters.add(Character("Стесняшка", R.drawable.shy))
                    "swimmer" -> characters.add(Character("Пловец", R.drawable.swimmer))

                    "sherlock" -> characters.add(Character("Шерлок", R.drawable.sherlock))
                    "fighter" -> characters.add(Character("Драчун", R.drawable.fighter))
                    "fidget" -> characters.add(Character("Непоседа", R.drawable.fidget))
                    "angel" -> characters.add(Character("Ангел", R.drawable.angel))

                }
            }
        }


        characterAdapter = CharacterAdapter(characters)
        characterList.adapter = characterAdapter
        characterAdapter.notifyDataSetChanged()

        if(pet.photos != null){
            photoAdapter = PhotoAdapter(pet.photos!!)
            photoList.adapter = photoAdapter
            photoAdapter.notifyDataSetChanged()
        }


        initialize(pet)

        return view
    }

    override fun initializePresenter() {
    }


}


