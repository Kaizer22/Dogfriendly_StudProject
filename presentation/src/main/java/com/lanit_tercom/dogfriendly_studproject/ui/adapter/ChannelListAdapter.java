package com.lanit_tercom.dogfriendly_studproject.ui.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.ChannelListPresenter;
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
    private List<UserModel> channelMembers;
    private List<ChannelModel> pinnedChannels;
    private MessageModel lastMessageModel;
    private Context context;

    private ChannelListPresenter channelPresenter;


    public ChannelListAdapter(Context context, ChannelListPresenter channelPresenter){
        this.channels = new ArrayList<>();
        this.channelMembers = new ArrayList<>();
        this.pinnedChannels = new ArrayList<>();
        this.context = context;
        this.channelPresenter =channelPresenter;
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
        holder.setUserProfileImage(getUserAvatar(channelModel));
        holder.setUserReceiverName(channelModel.getName());
        holder.setLastMessage(channelModel.getLastMessage());
        holder.setLastMessageTime(getLastMessageTime(channelModel));

        holder.itemView.setOnClickListener(v -> {
            String channelId = channelModel.getId();
            if (context != null && context instanceof MainNavigationActivity){
                ((MainNavigationActivity) context).navigateToChat(channelId);
            }
        });

        if (channelModel.isNotification()){
            holder.setNotificationStatusImage(R.drawable.ic_baseline_turn_on_ntf);
            holder.setNotificationStatusText(R.string.channel_notification_turn_on_button);
            holder.setOffNotifications(true);
        }
        else {
            holder.setNotificationStatusImage(R.drawable.ic_baseline_volume_off_24);
            holder.setNotificationStatusText(R.string.channel_notification_turn_off_button);
            holder.setOffNotifications(false);
        }

        if (channelModel.isPinned()){
            holder.setPinnedStatusImage(R.drawable.pin_channel);
            holder.setPinnedStatusText(R.string.channel_unpin_button);
            holder.setPinned(true);
        }
        else {
            holder.setPinnedStatusImage(R.drawable.pin_channel);
            holder.setPinnedStatusText(R.string.channel_pin_button);
            holder.setPinned(false);
        }

    }

    public void navigateToChat(int position){
        ChannelModel channelModel = channels.get(position);
        String channelId = channelModel.getId();
        if (context != null && context instanceof MainNavigationActivity){
            ((MainNavigationActivity) context).navigateToChat(channelId);
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
        pinnedChannels.clear();

        for (ChannelModel channel: channelList){
            if (channel.isPinned())
                pinnedChannels.add(channel);
            else channels.add(channel);
        }
        Collections.sort(channels, Collections.reverseOrder());
        for (ChannelModel channel: pinnedChannels)
            channels.add(0, channel);
        notifyDataSetChanged();
    }

    public void setChannelMembers(List<UserModel> users){
        validateMembersList(users);
        channelMembers.clear();
        channelMembers.addAll(users);
        notifyDataSetChanged();
    }

    public void setLastMessage(MessageModel messageModel){
        this.lastMessageModel = messageModel;
        notifyDataSetChanged();
    }

    public MessageModel getLastMessageModel(){
        return lastMessageModel;
    }

    public ChannelModel getChannelByID(int position){
        return channels.get(position);
    }

    private void validateChannelList(List<ChannelModel> channelList){
        if (channelList == null){
            throw new IllegalArgumentException("List of channels cannot be null... ChannelListAdapter");
        }
    }

    private void validateMembersList(List<UserModel> users){
        if (users == null){
            throw new IllegalArgumentException("List of channels cannot be null... ChannelListAdapter");
        }
    }

    private Uri getUserAvatar(ChannelModel channelModel){
        AuthManagerFirebaseImpl authManagerFirebase = new AuthManagerFirebaseImpl();
        Uri avatar = null;
        String userId = authManagerFirebase.getCurrentUserId();
        for (String memberId: channelModel.getMembers()){
            if (!memberId.equals(authManagerFirebase.getCurrentUserId())){
                userId = memberId;
                break;
            }
        }
        for (UserModel userModel: channelMembers) {
            if ((userModel.getId()).equals(userId)) {
                avatar = userModel.getAvatar();
                break;
            }
        }

        if (avatar == null){
            Resources resources = context.getResources();
            Uri.Builder builder = new Uri.Builder();
            builder.scheme(ContentResolver.SCHEME_ANDROID_RESOURCE);
            builder.authority(resources.getResourcePackageName(R.drawable.ic_user_profile_image));
            Uri uri = builder.build();
            avatar = uri;
            }
        return avatar;
    }

    private String getLastMessageTime(ChannelModel channelModel){
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
            if (time%10 == 1 && time != 11)
                lastMessageTime = time + " день";
            else if (time>4 && time<21)
                lastMessageTime = time + " дней";
            else if (time%10 < 4 || (time>19))
                lastMessageTime = time + " дня";
            else
                lastMessageTime = time + " дней";
        }
        else lastMessageTime = diff / 60000L/60/24/365 + " г";

        return lastMessageTime;
    }

}
