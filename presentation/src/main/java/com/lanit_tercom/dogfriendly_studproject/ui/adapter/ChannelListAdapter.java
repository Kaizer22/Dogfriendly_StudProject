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
import com.lanit_tercom.dogfriendly_studproject.ui.activity.MainNavigationActivity;
import com.lanit_tercom.dogfriendly_studproject.ui.viewholder.ChannelListViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListViewHolder> {

    private LayoutInflater inflater;
    private List<ChannelModel> channels;
    private Context context;


    public ChannelListAdapter(Context context){
        this.channels = new ArrayList<>();
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
        //holder.setUserReceiverName(channelModel.getLastMessageOwner());
        holder.setUserReceiverName(channelModel.getName());
        holder.setLastMessage(channelModel.getLastMessage());
        //holder.setLastMessageTime(channelModel.getTimestamp().toString());
        Date date = new Date(channelModel.getTimestamp());
        Date currentDate = new Date();
        String lastMessageTime = "";
        long diff = currentDate.getTime() - date.getTime();
        if (diff < 60000){
            lastMessageTime = "сейчас";
        }
        else if(diff < 3600000){
            lastMessageTime = diff/60000 + " мин";
        }
        else if(diff < 60000 * 60 * 24){
            lastMessageTime = diff/60000/60 + " ч";
        }
        else if((diff > 60000 * 60 * 24) && (diff < 60000 * 60 * 24*2)){
            lastMessageTime = "вчера";
        }
        else if ((diff > 60000 * 60 * 24*2) && (diff < 60000L*60*24*365)) {
            long time = diff / 60 / 60000 / 24;
            if (time%10 < 4)
                lastMessageTime = time + " дня";
            else
                lastMessageTime = time + " дней";
        }
        else lastMessageTime = diff / 60000L/60/24/365 + " г";


        //holder.setLastMessageTime(new SimpleDateFormat("dd.MM.YYYY hh:mm", Locale.UK).format(currentDate));
        holder.setLastMessageTime(lastMessageTime);

        holder.itemView.setOnClickListener(v -> {
            //String channelId = channelModel.getId();
            if (context != null && context instanceof MainNavigationActivity){
                ((MainNavigationActivity) context).navigateToChat(channelModel);
            }
        });
    }

    public void navigate(int position){
        ChannelModel channelModel = channels.get(position);
        //String channelId = channelModel.getId();
        if (context != null && context instanceof MainNavigationActivity){
            ((MainNavigationActivity) context).navigateToChat(channelModel);
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
        Collections.reverse(channels);
        notifyDataSetChanged();
    }

    public ChannelModel getChannelByID(int position){
        return channels.get(position);
    }

    private void validateChannelList(List<ChannelModel> channelList){
        if (channelList == null){
            throw new IllegalArgumentException("List of channels cannot be null... ChannelListAdapter");
        }
    }
}
