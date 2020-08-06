package com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lanit_tercom.dogfriendly_studproject.R

/**
 * Когда элементу устанавливается ширина match_parent -
 * проблемы возникают уже в PetDetail (слишком широкие элементы на весь экран при прокрутке)
 */
class CharacterAdapter(private val images: List<Int>, private val names: List<String>, private var onClickInterface: OnClickInterface?) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>(){


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var picked: Boolean = false
        var characterImage: ImageView = view.findViewById(R.id.character_image)
        var characterName: TextView = view.findViewById(R.id.character_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.pet_character_element, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.characterImage.setImageResource(images[position])
        holder.characterName.text = names[position]
        if(onClickInterface != null){
            holder.itemView.setOnClickListener{
                onClickInterface!!.setClick(position)

//                easy way doesn't work here too
//                if(holder.picked){
//                    holder.itemView.setBackgroundResource(R.drawable.ic_character_picked)
//                } else{
//                    holder.itemView.setBackgroundResource(R.drawable.ic_white_rectangle)
//                }
//
//                holder.picked = !holder.picked
            }

        }

    }

}