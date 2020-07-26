package com.lanit_tercom.dogfriendly_studproject.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.ChatView;
import com.lanit_tercom.dogfriendly_studproject.ui.viewholder.MessageViewHolder;

import java.util.LinkedList;
import java.util.List;

/**
 *  Адаптер, для вывода объектов в RecyclerView
 *  @author dshebut@rambler.ru
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private List<MessageModel> messages = new LinkedList<>();
    private String currentUserID;
    private ChatView chatView;

    public MessageAdapter(ChatView chatView, String currentUserID){
        this.currentUserID = currentUserID;
        this.chatView = chatView;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent,false);
        return new MessageViewHolder(view, chatView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageModel messageOnBind = messages.get(position);
        boolean isSentByCurrentUser = messageOnBind.getSenderID().equals(currentUserID);

        holder.bind(messageOnBind, isSentByCurrentUser, position);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setMessages(List<MessageModel> messages){
        this.messages.clear();
        this.messages.addAll(messages);
        notifyDataSetChanged();
    }
}
