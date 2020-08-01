package com.lanit_tercom.dogfriendly_studproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lanit_tercom.dogfriendly_studproject.R
import kotlinx.android.synthetic.main.near_list_item_view_test.view.*

class DogAdapter(val names: Array<String>, val imageIds: Array<Int>, val distances: Array<Int>): RecyclerView.Adapter<DogAdapter.ViewHolder>() {

    inner class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView){
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.near_list_item_view_test,
                parent,
                false) as CardView
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.cardView
        val imageView = cardView.image_dog_icon
        val drawable = ContextCompat.getDrawable(cardView.context, imageIds[position])
        imageView.setImageDrawable(drawable)
        val textViewDogName = cardView.textView_dog_name
        textViewDogName.text = names[position]
        val textViewDistance = cardView.textView_distance
        textViewDistance.text = "${distances[position]} км от вас"
    }
}