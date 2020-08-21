package com.lanit_tercom.dogfriendly_studproject.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.Point;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.WalkModel;
import com.lanit_tercom.dogfriendly_studproject.ui.viewholder.WalkMemberListViewHolder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class WalkMemberListAdapter extends RecyclerView.Adapter<WalkMemberListViewHolder>{

    LayoutInflater inflater;
    List<UserModel> walkMembers;
    Context context;



    public WalkMemberListAdapter(Context context){
        this.walkMembers = new LinkedList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public WalkMemberListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.walk_member_item_layout, parent, false);
        return new WalkMemberListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalkMemberListViewHolder holder, int position) {
        UserModel userModel = walkMembers.get(position);
        holder.setMemberImage(userModel.getAvatar());
        holder.setMemberName(userModel.getName());
        holder.setMemberAge(userModel.getAge().toString());

    }

    @Override
    public int getItemCount() {
        return (this.walkMembers != null) ? this.walkMembers.size() : 0;
    }

    public void setWalkMembers(List<UserModel> members){
        validateMemberList(members);
        walkMembers.clear();
        walkMembers.addAll(members);
        notifyDataSetChanged();
    }

   /* public UserModel getWalkMemberById(int position){
        return walkMembers.get(position);
    }*/

    private void validateMemberList(List<UserModel> members){
        if (members == null){
            throw new IllegalArgumentException("List of channels cannot be null.");
        }
    }


    //TODO delete method
    /*public void setWalkMembers(){
        walkMembers = new ArrayList<>();
        walkMembers.add(new UserModel("1", "Mark", 22, "mark@mail.ru", "123456",
                "заглушка в WalkMemberListAdapter","заглушка в WalkMemberListAdapter",
                //Uri.parse("@drawable/ic_user_profile_image.jpg"),
                Uri.parse(context.getResources().getResourcePackageName(R.drawable.ic_user_profile_image)),
                new LinkedList<PetModel>(), new Point(1.0, 1.0)));
        walkMembers.add(new UserModel("2", "Ivan", 22, "ivan@mail.ru", "123456",
                "заглушка в WalkMemberListAdapter","заглушка в WalkMemberListAdapter",
                Uri.parse("@drawable/ic_user_profile_image.jpg"),
                new LinkedList<PetModel>(), new Point(1.0, 10.0)));
        walkMembers.add(new UserModel("3", "Ben", 22, "ben@mail.ru", "123456",
                "заглушка в WalkMemberListAdapter","заглушка в WalkMemberListAdapter",
                Uri.parse("@drawable/ic_user_profile_image.jpg"),
                new LinkedList<PetModel>(), new Point(1.0, 54.0)));
    }*/
}
