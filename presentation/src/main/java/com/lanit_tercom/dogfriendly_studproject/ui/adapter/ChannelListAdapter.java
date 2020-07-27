package com.lanit_tercom.dogfriendly_studproject.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.ChannelEntityDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.data.repository.ChannelRepositoryImpl;
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread;
import com.lanit_tercom.dogfriendly_studproject.mapper.ChannelModelDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;
import com.lanit_tercom.dogfriendly_studproject.ui.activity.ChannelListActivity;
import com.lanit_tercom.dogfriendly_studproject.ui.viewholder.ChannelListViewHolder;
import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.channel.GetChannelsUseCase;
import com.lanit_tercom.domain.interactor.channel.impl.GetChannelsUseCaseImpl;
import com.lanit_tercom.domain.repository.ChannelRepository;
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListViewHolder> {

    private LayoutInflater inflater;
    private List<ChannelModel> channels;
    //private String currentUserID;

    public ChannelListAdapter(Context context){ //need channels?? TODO List<ChannelModel> channels
        this.channels = new LinkedList<>();
        inflater = LayoutInflater.from(context);
        //currentUserID = authManager.getCurrentUserId();
    }

    @NonNull
    @Override
    public ChannelListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_channel_item_layout, parent, false);
        return new ChannelListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelListViewHolder holder, int position) {
        ChannelModel channelModel = channels.get(position);
        // TODO Change image provider
        holder.setUserProfileImage(R.drawable.ic_user_profile_image);
        // TODO Change ID to Name
        holder.setUserReceiverName(channelModel.getLastMessageOwner());
        holder.setLastMessage(channelModel.getLastMessage());
        holder.setLastMessageTime(channelModel.getTimestamp().toString());
    }

    @Override
    public int getItemCount() {
        /**
         * Error is here^ channels.size = 0
         */
        Log.e("ChannelList Size", String.valueOf(channels.size()));
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
}
