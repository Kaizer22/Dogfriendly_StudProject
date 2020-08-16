package com.lanit_tercom.dogfriendly_studproject.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserDetailFragment

class PetListAdapter(private val items: ArrayList<UserDetailFragment.PetListItem>): RecyclerView.Adapter<PetListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.dog_avatar_preview)
        val name: TextView = itemView.findViewById(R.id.dog_name)
        val desc: TextView = itemView.findViewById(R.id.dog_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.pet_list_item_test, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(items[position].uri!= null)
            Glide.with(holder.itemView.context)
                    .load(Uri.parse(items[position].uri))
                    .circleCrop()
                    .into(holder.avatar)
        holder.name.text = items[position].name
        holder.desc.text = items[position].desc

    }
}