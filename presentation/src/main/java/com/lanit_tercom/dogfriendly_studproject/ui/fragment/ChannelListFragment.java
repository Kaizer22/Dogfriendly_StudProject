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
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.ChannelListPresenter;
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.ChannelListAdapter;
import com.lanit_tercom.domain.interactor.get.GetChannelListImpl;


public class ChannelListFragment extends BaseFragment {

    ChannelListPresenter channelListPresenter;

    public ChannelListFragment() {
        // Required empty public constructor
    }

    @Override
    public void initializePresenter() {
        AuthManager authManager = new AuthManagerFirebaseImpl();
        channelListPresenter = new ChannelListPresenter( );
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_channel_list, container, false);

        RecyclerView recyclerViewForChannels = view.findViewById(R.id.rv_channel);
        recyclerViewForChannels.setLayoutManager(new LinearLayoutManager(getActivity()));



        ChannelListAdapter dialogListAdapter = new ChannelListAdapter(getContext(), ChannelsProviderTemp.getChannels());
        recyclerViewForChannels.setAdapter(dialogListAdapter);

        return view;
    }


}