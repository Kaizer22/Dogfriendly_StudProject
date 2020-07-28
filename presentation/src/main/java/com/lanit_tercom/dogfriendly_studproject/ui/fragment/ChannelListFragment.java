package com.lanit_tercom.dogfriendly_studproject.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.entity.ChannelEntity;
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.ChannelCache;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.ChannelEntityDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.data.repository.ChannelRepositoryImpl;
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.ChannelListPresenter;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.ChannelListView;
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.ChannelListAdapter;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.channel.AddChannelUseCase;
import com.lanit_tercom.domain.interactor.channel.DeleteChannelUseCase;
import com.lanit_tercom.domain.interactor.channel.GetChannelsUseCase;
import com.lanit_tercom.domain.interactor.channel.impl.AddChannelUseCaseImpl;
import com.lanit_tercom.domain.interactor.channel.impl.DeleteChannelUseCaseImpl;
import com.lanit_tercom.domain.interactor.channel.impl.GetChannelsUseCaseImpl;
import com.lanit_tercom.library.data.manager.NetworkManager;
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ChannelListFragment extends BaseFragment implements ChannelListView {

    ChannelListPresenter channelListPresenter;

    List<ChannelModel> channels;
    RecyclerView channelListRecyclerView;
    ChannelListAdapter channelListAdapter;

    public interface ChannelListListener{
        void onChannelClicked(final ChannelModel channelModel);
    }



    private ChannelListListener channelListListener;

    /**
     * For test
     */
    String userId = "2345"; //58CktVjke1frVUW9YirSQVlXt2x1 1OUgqDel92RLeacUFajLizVxWyk2 gZApuK3QxMhXT0PPtsVs4eeo5Dp2

    public ChannelListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ChannelListListener){
            this.channelListListener = (ChannelListListener) context;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        this.channelListListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_list, container, false);

        channelListPresenter.setView(this);
        initRecycleView(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.channelListPresenter.setView(this);
        if (savedInstanceState == null){
            this.loadChannelList();
        }

    }

    @Override
    public void initializePresenter() {
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        //AuthManager authManager = new AuthManagerFirebaseImpl();
        NetworkManager networkManager = new NetworkManagerImpl(getContext());
        ChannelEntityDtoMapper dtoMapper = new ChannelEntityDtoMapper();
        ChannelCache channelCache = new ChannelCache() {
            @Override
            public void saveChannel(String messageId, ChannelEntity channelEntity) {

            }
        };

        ChannelEntityStoreFactory channelEntityStoreFactory = new ChannelEntityStoreFactory(networkManager, channelCache);
        ChannelRepositoryImpl channelRepository = ChannelRepositoryImpl.getInstance(channelEntityStoreFactory, dtoMapper);

        GetChannelsUseCase getChannelsUseCase = new GetChannelsUseCaseImpl(channelRepository, threadExecutor, postExecutionThread);
        AddChannelUseCase addChannelUseCase = new AddChannelUseCaseImpl(channelRepository, threadExecutor, postExecutionThread);
        DeleteChannelUseCase deleteChannelUseCase = new DeleteChannelUseCaseImpl(channelRepository, threadExecutor, postExecutionThread);

        channelListPresenter = new ChannelListPresenter(userId, getChannelsUseCase, addChannelUseCase, deleteChannelUseCase);
        channelListPresenter.refreshChannelsData();
    }

    @Override
    public void showUnreadMessage() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(@NotNull String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public void initRecycleView(@NotNull View view){
        // TODO connect onClickListener
        //onItemClickListener = null
        //this.channelListAdapter.setOnItemClickListener(onItemClickListener);
        channelListRecyclerView = view.findViewById(R.id.rv_channel);
        channelListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        channelListAdapter = new ChannelListAdapter(getContext());
        channelListRecyclerView.setAdapter(channelListAdapter);
    }

    private void loadChannelList(){
        this.channelListPresenter.initialize();
    }

    @Override
    public void renderChannels(List<ChannelModel> channels){
        channelListAdapter.setChannels(channels); // channelListPresenter.getChannelList()
    }

    @Override
    public void viewChannel(ChannelModel channelModel) {
        if (this.channelListListener != null){
            this.channelListListener.onChannelClicked(channelModel);
        }
    }

    private ChannelListAdapter.OnItemClickListener onItemClickListener =
            channelModel -> {
                if (ChannelListFragment.this.channelListPresenter != null && channelModel != null){
                    ChannelListFragment.this.channelListPresenter.onChannelClicked(channelModel);
                }
            };
}