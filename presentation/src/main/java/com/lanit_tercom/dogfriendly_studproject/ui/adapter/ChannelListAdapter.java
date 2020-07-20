package com.lanit_tercom.dogfriendly_studproject.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelListModel;
import com.lanit_tercom.dogfriendly_studproject.ui.viewholder.ChannelListViewHolder;

import java.util.List;

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListViewHolder> {

    private LayoutInflater inflater;
    private List<ChannelListModel> channels;
    private String currentUserID;

    public ChannelListAdapter(Context context, List<ChannelListModel> channels, AuthManager authManager){
        this.channels = channels;
        inflater = LayoutInflater.from(context);
        currentUserID = authManager.getCurrentUserId();
    }

    @NonNull
    @Override
    public ChannelListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_channel_item_layout, parent, false);
        return new ChannelListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelListViewHolder holder, int position) {
        ChannelListModel dialogModel = channels.get(position);
        // TODO Change image provider
        holder.setUserProfileImage(R.drawable.ic_user_profile_image);
        // TODO Change ID to Name
        holder.setUserReceiverName(dialogModel.getReceiverID());
        holder.setLastMessage(dialogModel.getLastMessage());
        holder.setLastMessageTime(dialogModel.getLastMessageTime());
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }
}
