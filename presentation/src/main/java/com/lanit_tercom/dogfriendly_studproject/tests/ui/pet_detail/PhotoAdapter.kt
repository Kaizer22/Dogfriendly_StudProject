package com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail

import android.graphics.drawable.ScaleDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lanit_tercom.dogfriendly_studproject.R
import kotlinx.android.synthetic.main.pet_photo_element.view.*


class PhotoAdapter(private val images: List<Int>) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    inner class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView: CardView = LayoutInflater.from(parent.context).inflate(R.layout.pet_photo_element, parent, false) as CardView
        return ViewHolder(cardView)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.cardView
        val imageView = cardView.photo_image
        val drawable = ContextCompat.getDrawable(cardView.context, images[position])
        ScaleDrawable(drawable, 11, 50.0F, 50.0F)
        imageView.setImageDrawable(drawable)
    }

}