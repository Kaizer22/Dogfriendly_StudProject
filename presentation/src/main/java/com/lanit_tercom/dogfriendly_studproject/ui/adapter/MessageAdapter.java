package com.lanit_tercom.dogfriendly_studproject.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanit_tercom.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel;
import com.lanit_tercom.dogfriendly_studproject.ui.viewholder.MessageViewHolder;

import java.util.List;

/**
 *  Адаптер, для вывода объектов в RecyclerView
 *  @author dshebut@rambler.ru
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private List<MessageModel> messages;
    private String currentUserID;
    private LayoutInflater inflater;

    public MessageAdapter(Context context, List<MessageModel> messages, AuthManager authManager){
        this.messages = messages;
        currentUserID = authManager.getCurrentUserId();
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_message, parent,false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageModel messageOnBind = messages.get(position);
        boolean isSentByCurrentUser = messageOnBind.getSenderID().equals(currentUserID);

        holder.changeMessagePosition(isSentByCurrentUser);
        holder.changeMessageBackground(isSentByCurrentUser);

        holder.setText(messageOnBind.getText());
        holder.setTime(messageOnBind.getTime());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
