package com.lanit_tercom.dogfriendly_studproject.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.walk.FirebaseWalkEntityStore;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.WalkModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.WalkPresenter;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.WalkDetailsView;
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.WalkMemberListAdapter;

import org.jetbrains.annotations.NotNull;

public class WalkFragment extends BaseFragment implements WalkDetailsView {

    RecyclerView walkMemberListRecyclerView;
    WalkMemberListAdapter walkMemberListAdapter;
    //WalkPresenter walkPresenter;


    public WalkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_walk, container, false);

        //walkPresenter.setView(this);

        initRecyclerView(view);

        //TODO test method
        //testWalkFirebase();

        return view;
    }

    @Override
    public void initializePresenter() {

    }

    private void initRecyclerView(@NotNull View view){
        walkMemberListRecyclerView = view.findViewById(R.id.rv_walk_members);
        walkMemberListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        walkMemberListAdapter = new WalkMemberListAdapter(getContext());
        walkMemberListAdapter.setWalkMembers();
        walkMemberListRecyclerView.setAdapter(walkMemberListAdapter);
    }

    @Override
    public void renderCurrentWalk(WalkModel walkModel) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(@NotNull String message) {

    }

    private void testWalkFirebase(){
        FirebaseWalkEntityStore firebaseWalkEntityStore = new FirebaseWalkEntityStore();
    }
}