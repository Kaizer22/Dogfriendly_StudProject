package com.lanit_tercom.dogfriendly_studproject.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.ChannelsProviderTemp;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserChannelListPresenter;
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.ChannelListAdapter;


public class UserChannelListFragment extends BaseFragment {

    UserChannelListPresenter userDialogListPresenter;

    public UserChannelListFragment() {
        // Required empty public constructor
    }

    @Override
    public void initializePresenter() {
        AuthManager authManager = new AuthManagerFirebaseImpl();
        userDialogListPresenter = new UserChannelListPresenter(authManager);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_channel_list, container, false);

        RecyclerView recyclerViewForChannels = view.findViewById(R.id.rv_channel);
        recyclerViewForChannels.setLayoutManager(new LinearLayoutManager(getActivity()));



        ChannelListAdapter dialogListAdapter = new ChannelListAdapter(getContext(), ChannelsProviderTemp.getChannels(),
                userDialogListPresenter.getAuthManager());
        recyclerViewForChannels.setAdapter(dialogListAdapter);

        return view;
    }


}