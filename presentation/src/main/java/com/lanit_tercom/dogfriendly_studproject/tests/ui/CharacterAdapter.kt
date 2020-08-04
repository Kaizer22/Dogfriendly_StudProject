package com.lanit_tercom.dogfriendly_studproject.tests.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lanit_tercom.dogfriendly_studproject.R

/**
 * pet_character_element используется на двух разных экранах - выбор характера и окно питомца
 * При этом требуется, что бы выделение элементаа зеленой рамкой было только в выборе характера, но не в окне питомца
 * Вот это пока не получилось - есть закомменченный вариант, который срабатывает везде и еще с косячком работает
 */
class CharacterAdapter(private val images: List<Int>, private val names: List<String>) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var characterImage: ImageView = view.findViewById(R.id.character_image)
        var characterName: TextView = view.findViewById(R.id.character_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.pet_character_element, parent, false)

        //Не подходит для разных экранов и вообще работает с косяками (для того чтоб работало надо нажать два раза, потом все ок)
//        val PICKED: Drawable? = ContextCompat.getDrawable(view.context, R.drawable.ic_character_picked)
//        val NOT_PICKED: Drawable? = ContextCompat.getDrawable(view.context, R.drawable.ic_white_rectangle)
//
//        view.setOnClickListener {
//
//            if (view.character_linear_layout.background == NOT_PICKED){
//                view.character_linear_layout.background = PICKED
//            } else {
//                view.character_linear_layout.background = NOT_PICKED
//            }
//        }

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: CharacterAdapter.ViewHolder, position: Int) {
        holder.characterImage.setImageResource(images[position])
        holder.characterName.text = names[position]
    }

}