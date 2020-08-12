package com.lanit_tercom.dogfriendly_studproject.ui.viewholder;

import android.view.View;
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

    public ChannelListViewHolder(@NonNull View itemView){
        super(itemView);

        userProfileImage = itemView.findViewById(R.id.user_profile_image);
        userReceiverName = itemView.findViewById(R.id.user_receiver_name);
        lastMessage = itemView.findViewById(R.id.last_message);
        lastMessageTime = itemView.findViewById(R.id.last_message_time);
    }

    public void setUserProfileImage(int imageID){
        userProfileImage.setImageResource(imageID);
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
}
