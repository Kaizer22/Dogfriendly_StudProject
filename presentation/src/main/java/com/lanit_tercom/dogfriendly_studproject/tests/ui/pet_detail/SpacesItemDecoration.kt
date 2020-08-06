package com.lanit_tercom.dogfriendly_studproject.tests.ui.pet_detail

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView
import android.view.View;

/**
 * Это для того чтоб таблицам через код размер задавать.
 */
class SpacesItemDecoration(private var space: Int): RecyclerView.ItemDecoration(){


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = space
        outRect.right = space
        outRect.bottom = space

        if(parent.getChildLayoutPosition(view) == 0){
            outRect.top = space;
        } else {
            outRect.top = space;
        }
    }

}
