package com.lanit_tercom.dogfriendly_studproject.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanit_tercom.dogfriendly_studproject.ui.viewholder.MessageViewHolder;

/**
 *  Адаптер, для вывода объектов в RecyclerView
 *  @author dshebut@rambler.ru
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
