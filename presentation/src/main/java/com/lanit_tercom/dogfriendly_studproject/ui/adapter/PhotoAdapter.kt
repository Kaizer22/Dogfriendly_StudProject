package com.lanit_tercom.dogfriendly_studproject.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lanit_tercom.dogfriendly_studproject.R

class PhotoAdapter(val images: ArrayList<Uri>) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    class ViewHolder(private var view: View) : RecyclerView.ViewHolder(view){
        val imageView = view.findViewById<ImageView>(R.id.photo_background)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.pet_photo_element, parent, false)
        return PhotoAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
                .load(images[position])
                .into(holder.imageView)
    }
}