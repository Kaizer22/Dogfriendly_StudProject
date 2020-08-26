package com.lanit_tercom.dogfriendly_studproject.ui.viewholder;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.library.presentation.ui.viewholder.BaseViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChannelListViewHolder extends BaseViewHolder {

    private CircleImageView userProfileImage;
    private TextView userReceiverName;
    private TextView lastMessage;
    private TextView lastMessageTime;
    private ImageView pinnedChannel;
    private ImageView turnNotifications;

    private ImageView notificationStatusImage;
    private TextView notificationStatusText;
    private ImageView pinnedStatusImage;
    private TextView pinnedStatusText;

    public ChannelListViewHolder(@NonNull View itemView){
        super(itemView);

        userProfileImage = itemView.findViewById(R.id.user_profile_image);
        userReceiverName = itemView.findViewById(R.id.user_receiver_name);
        lastMessage = itemView.findViewById(R.id.last_message);
        lastMessageTime = itemView.findViewById(R.id.last_message_time);

        pinnedChannel = itemView.findViewById(R.id.pin_channel_icon);
        turnNotifications = itemView.findViewById(R.id.turn_off_ntf_icon);

        this.notificationStatusImage = itemView.findViewById(R.id.img_turnOffNotification);
        this.notificationStatusText = itemView.findViewById(R.id.channel_turnOff__ntf_text);
        this.pinnedStatusImage = itemView.findViewById(R.id.img_pin_message);
        this.pinnedStatusText = itemView.findViewById(R.id.channel_pin_text);
    }

    public void setUserProfileImage(Uri imageID){
        userProfileImage.setImageURI(imageID);
    }

    public void setUserReceiverName(String userName) {
        userReceiverName.setText(userName);
    }

    public void setLastMessage(String message){
        lastMessage.setText(message);
    }

    public void setLastMessageTime(String messageTime){
        lastMessageTime.setText(messageTime);
    }

    public void setPinned(boolean status){
        if (status){
            pinnedChannel.setVisibility(View.VISIBLE);
        }
        else
            pinnedChannel.setVisibility(View.GONE);
    }

    public void setOffNotifications(boolean status){
        if (status){
            turnNotifications.setVisibility(View.VISIBLE);
        }
        else
            turnNotifications.setVisibility(View.GONE);
    }

    public void setNotificationStatusImage(int imageId){
        notificationStatusImage.setImageResource(imageId);
    }

    public void setNotificationStatusText(int stringId){
        notificationStatusText.setText(stringId);
    }

    public void setPinnedStatusImage(int imageId){
        pinnedStatusImage.setImageResource(imageId);
    }

    public void setPinnedStatusText(int stringId){
        pinnedStatusText.setText(stringId);
    }
}
