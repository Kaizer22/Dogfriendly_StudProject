package com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail


import android.net.Uri
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lanit_tercom.dogfriendly_studproject.R


/**
 * Да ну его, это невозможно в данном случае
 */
class PhotoAdapter(val images: ArrayList<Uri>, val onPhotoListener: OnPhotoListener) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {


    class ViewHolder(private var view: View, private var onPhotoListener: OnPhotoListener) : RecyclerView.ViewHolder(view) , View.OnClickListener{
        var photoImage: ImageView = view.findViewById(R.id.photo_image)
        var photoBackground: CardView = view.findViewById(R.id.photo_background)
        val removePhoto: ImageButton= view.findViewById(R.id.remove_photo)

        init{
            view.setOnClickListener(this)
        }



        override fun onClick(v: View?) {
            if(adapterPosition == 0){
                onPhotoListener.onPhotoClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.pet_photo_element, parent, false)
        return PhotoAdapter.ViewHolder(view, onPhotoListener)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.photoImage.setImageURI(images[position])
        if(position == 0){
            holder.removePhoto.visibility = View.INVISIBLE
            holder.photoBackground.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_gray_rectangle)

        } else {
            holder.removePhoto.setOnClickListener {
                val temp = images[position]
                images.removeAt(position)
                images.add(Uri.parse("android.resource://com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail/" + R.drawable.ic_button_add_photo))
                notifyDataSetChanged()
            }
        }

    }

    interface OnPhotoListener{
        fun onPhotoClick(position: Int)
    }

}