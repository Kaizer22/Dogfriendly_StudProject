package com.lanit_tercom.library.presentation.ui.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 *  Базовый ViewHolder от которого наследуются все остальные
 *  @author dshebut@rambler.ru
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
