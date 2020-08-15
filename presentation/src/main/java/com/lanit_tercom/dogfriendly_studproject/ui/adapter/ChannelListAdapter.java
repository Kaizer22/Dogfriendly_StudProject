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
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListViewHolder> {

    private LayoutInflater inflater;
    private List<ChannelModel> channels;
    private Context context;
    //private String currentUserID;

    /*public interface OnItemClickListener{
        void onChannelItemClicked(ChannelModel channelModel);
    }*/

    //private OnItemClickListener onItemClickListener;

    public ChannelListAdapter(Context context){ //need channels?? TODO List<ChannelModel> channels
        this.channels = new LinkedList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
        //currentUserID = authManager.getCurrentUserId();
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
            Log.e("Click", "the item was clicked");
            //TODO navigation to chosen chat (channelId)
            String channelId = channelModel.getId();
            //TODO тестовый код
            if (context != null && context instanceof MainNavigationActivity){
                ((MainNavigationActivity) context).navigateToChat("-MCqwIrhuEPqkgz1GV18"); // channelId
            }

            //if (context != null && context instanceof ChannelListActivity){
                //((ChannelListActivity) context).navigateToChat(); // channelId
            //}
/*
            if (ChannelListAdapter.this.onItemClickListener != null){
                ChannelListAdapter.this.onItemClickListener.onChannelItemClicked(channelModel);
                Log.e("Click", "the item was clicked");
            }
            else Log.e("Click", "onItemClickListener = null");*/
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("Click", "the item was clicked long");

                Toast.makeText(context, "IT WAS LONG CLICK", Toast.LENGTH_LONG).show();
                return false;
            }
        });
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



    /*public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }*/


    public void setChannels(List<ChannelModel> channelList){
        this.validateChannelList(channelList);
        this.channels.clear();
        this.channels.addAll(channelList);
        this.notifyDataSetChanged();
    }

    private void validateChannelList(List<ChannelModel> channelList){
        if (channelList == null){
            throw new IllegalArgumentException("List of channels cannot be null... ChannelListAdapter");
        }
    }

    /*static class ChannelViewHolder extends RecyclerView.ViewHolder{

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }*/
}
