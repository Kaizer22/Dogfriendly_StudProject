package com.lanit_tercom.dogfriendly_studproject.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;
import com.lanit_tercom.dogfriendly_studproject.ui.activity.ChannelListActivity;
import com.lanit_tercom.dogfriendly_studproject.ui.viewholder.ChannelListViewHolder;
import java.util.LinkedList;
import java.util.List;

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListViewHolder> {

    private LayoutInflater inflater;
    private List<ChannelModel> channels;
    private Context context;


    public ChannelListAdapter(Context context){
        this.channels = new LinkedList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ChannelListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.channel_item_layout, parent, false);
        return new ChannelListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelListViewHolder holder, int position) {
        ChannelModel channelModel = channels.get(position);
        holder.setUserProfileImage(R.drawable.ic_user_profile_image);
        holder.setUserReceiverName(channelModel.getLastMessageOwner());
        holder.setLastMessage(channelModel.getLastMessage());
        holder.setLastMessageTime(channelModel.getTimestamp().toString());

        holder.itemView.setOnClickListener(v -> {
            String channelId = channelModel.getId();
            if (context != null && context instanceof ChannelListActivity){
                ((ChannelListActivity) context).navigateToChat(channelId);
            }
        });
    }

    public void navigate(int position){
        ChannelModel channelModel = channels.get(position);
        String channelId = channelModel.getId();
        if (context != null && context instanceof ChannelListActivity){
            ((ChannelListActivity) context).navigateToChat(channelId);
        }
    }

    @Override
    public int getItemCount() {
        return (this.channels != null) ? this.channels.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public void setChannels(List<ChannelModel> channelList){
        validateChannelList(channelList);
        channels.clear();
        channels.addAll(channelList);
        notifyDataSetChanged();
    }

    private void validateChannelList(List<ChannelModel> channelList){
        if (channelList == null){
            throw new IllegalArgumentException("List of channels cannot be null... ChannelListAdapter");
        }
    }
}
