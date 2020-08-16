package com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.lanit_tercom.dogfriendly_studproject.R


class CharacterAdapter(private val characters: List<Character>, private val onCharacterListener: OnCharacterListener?) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>(){

    class ViewHolder(private var view: View, private var onCharacterListener: OnCharacterListener?): RecyclerView.ViewHolder(view), View.OnClickListener{
        var picked: Boolean = false
        var characterImage: ImageView = view.findViewById(R.id.character_image)
        var characterName: TextView = view.findViewById(R.id.character_name)
        var background: MaterialCardView = view.findViewById(R.id.character_card_view)
        init{ view.setOnClickListener(this) }

        override fun onClick(v: View?) {
            onCharacterListener?.onCharacterClick(adapterPosition)

            if (!picked)
                background.strokeWidth= 5
            else
                background.strokeWidth = 0

            picked = !picked
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.pet_character_element, parent, false)
        return ViewHolder(view, onCharacterListener)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.characterImage.setImageResource(characters[position].getImage())
        holder.characterName.text = characters[position].getName()
    }

    interface OnCharacterListener{
        fun onCharacterClick(position: Int)
    }

}