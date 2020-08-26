package com.lanit_tercom.dogfriendly_studproject.ui.adapter

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


class CharacterAdapter(private val characters: List<Character>) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>(){

    class ViewHolder(private var view: View): RecyclerView.ViewHolder(view){
        var characterImage: ImageView = view.findViewById(R.id.character_image)
        var characterName: TextView = view.findViewById(R.id.character_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.pet_character_element, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.characterImage.setImageResource(characters[position].getImage())
        holder.characterName.text = characters[position].getName()
    }
}