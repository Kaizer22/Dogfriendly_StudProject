package com.lanit_tercom.dogfriendly_studproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lanit_tercom.dogfriendly_studproject.R
import kotlinx.android.synthetic.main.near_list_item_view_test.view.*

class DogAdapter(val names: Array<String>, val imageIds: Array<String>, val distances: Array<Double>, val breeds: Array<String>, val ages: Array<Int>, val tag: String ): RecyclerView.Adapter<DogAdapter.ViewHolder>() {

    private var listener: Listener? = null

    interface Listener{
        fun onClick(position: Int)
    }

    inner class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView){
    }

    fun setListener(listener: Listener){
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView: CardView
        cardView = if (tag == "map")
            LayoutInflater.from(parent.context).inflate(R.layout.near_list_item_view_test,
                    parent,
                    false) as CardView
        else if (tag == "map_settings")
            LayoutInflater.from(parent.context).inflate(R.layout.settings_list_dog_view_test,
                    parent,
                    false) as CardView
        else
            LayoutInflater.from(parent.context).inflate(R.layout.near_list_item_view_test,
                    parent,
                    false) as CardView
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.cardView
        val imageView = cardView.image_dog_icon
        Glide.with(cardView.context).load(imageIds[position]).into(imageView)
        val textViewDogName = cardView.textView_dog_name
        textViewDogName.text = names[position]
        val textViewDistance = cardView.textView_distance
        if (tag == "map")
            textViewDistance.text = "${distances[position]} км от вас"
        else
            textViewDistance.text = "${breeds[position]}, ${ages[position]} года"
        cardView.setOnClickListener {
            listener?.onClick(position)
        }
    }
}