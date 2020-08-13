package com.lanit_tercom.dogfriendly_studproject.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.Point;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.WalkModel;
import com.lanit_tercom.dogfriendly_studproject.ui.viewholder.WalkMemberListViewHolder;

import java.util.ArrayList;
import java.util.List;

public class WalkMemberListAdapter extends RecyclerView.Adapter<WalkMemberListViewHolder>{

    LayoutInflater inflater;
    List<UserModel> walkMembers;
    Context context;

    public WalkMemberListAdapter(Context context){
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
        holder.setMemberImage(R.drawable.ic_user_profile_image);
        holder.setMemberName(userModel.getName());


        //TODO сделать что-то с возрастом пользователя
        holder.setMemberAge("20");

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

    public UserModel getWalkMemberById(int position){
        return walkMembers.get(position);
    }

    private void validateMemberList(List<UserModel> members){
        if (members == null){
            throw new IllegalArgumentException("List of channels cannot be null.");
        }
    }

    public void setWalkMembers(){
        walkMembers = new ArrayList<>();
        walkMembers.add(new UserModel("1", "Mark", "mark@mail.ru", "111", new Point(1.0, 1.0)));
        walkMembers.add(new UserModel("2", "Vovan", "vovan@mail.ru", "111", new Point(1.0, 1.0)));
        walkMembers.add(new UserModel("3", "Yan", "yan@mail.ru", "111", new Point(1.0, 1.0)));
        walkMembers.add(new UserModel("4", "Lee", "lee@mail.ru", "111", new Point(1.0, 1.0)));
        walkMembers.add(new UserModel("5", "Astic", "astic@mail.ru", "111", new Point(1.0, 1.0)));

    }
}
