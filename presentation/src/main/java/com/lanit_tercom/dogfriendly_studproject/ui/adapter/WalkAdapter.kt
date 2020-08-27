package com.lanit_tercom.dogfriendly_studproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.domain.dto.PetDto
import kotlinx.android.synthetic.main.near_list_item_view_test.view.*

class WalkAdapter(val userIds: Array<String>, val walkIds: Array<String>, val walksNames: Array<String>, val walkDistances: Array<Int?>): RecyclerView.Adapter<WalkAdapter.ViewHolder>() {

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
        return walkIds.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView: CardView
        cardView = LayoutInflater.from(parent.context).inflate(R.layout.near_list_item_view_test,
                    parent,
                    false) as CardView
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.cardView
        val imageView = cardView.image_dog_icon
        Glide.with(cardView.context).load("https://firebasestorage.googleapis.com/v0/b/dogfriendlystudproject.appspot.com/o/Uploads%2FGroup%20312.png?alt=media&token=53830490-e267-421c-95fe-86d9391eee71").circleCrop().into(imageView)
        val textViewDogName = cardView.textView_dog_name
        textViewDogName.text = walksNames[position]
        val textViewDistance = cardView.textView_distance
        textViewDistance.text = "${walkDistances[position]} м от вас"
            cardView.setOnClickListener {
                listener?.onClick(position)
            }
        }

    }
