package com.lanit_tercom.dogfriendly_studproject.ui.viewholder;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.library.presentation.ui.viewholder.BaseViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

public class WalkMemberListViewHolder extends BaseViewHolder {

    private CircleImageView memberImage;
    private TextView memberName;
    private TextView memberAge;

    public WalkMemberListViewHolder(@NonNull View itemView) {
        super(itemView);

        memberImage = itemView.findViewById(R.id.walk_member_image);
        memberName = itemView.findViewById(R.id.walk_member_name);
        memberAge = itemView.findViewById(R.id.walk_member_age);
    }

    public void setMemberImage(Uri imageID) {  this.memberImage.setImageURI(imageID); }

    public void setMemberName(String memberName) {this.memberName.setText(memberName);}

    public void setMemberAge(String memberAge) {this.memberAge.setText(memberAge);}
}
