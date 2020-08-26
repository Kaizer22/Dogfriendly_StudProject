package com.lanit_tercom.dogfriendly_studproject.ui.fragment;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.data.entity.ChannelEntity;
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.ChannelCache;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.ChannelEntityDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.data.repository.ChannelRepositoryImpl;
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl;
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.ChannelListPresenter;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.ChannelRecyclerTouchListener;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.ChannelRecyclerTouchListener;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.ChannelListView;
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.ChannelListAdapter;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.channel.AddChannelUseCase;
import com.lanit_tercom.domain.interactor.channel.DeleteChannelUseCase;
import com.lanit_tercom.domain.interactor.channel.EditChannelUseCase;
import com.lanit_tercom.domain.interactor.channel.GetChannelsUseCase;
import com.lanit_tercom.domain.interactor.channel.impl.AddChannelUseCaseImpl;
import com.lanit_tercom.domain.interactor.channel.impl.DeleteChannelUseCaseImpl;
import com.lanit_tercom.domain.interactor.channel.impl.EditChannelUseCaseImpl;
import com.lanit_tercom.domain.interactor.channel.impl.GetChannelsUseCaseImpl;
import com.lanit_tercom.domain.interactor.user.GetUsersByIdUseCase;
import com.lanit_tercom.domain.interactor.user.impl.GetUsersByIdUseCaseImpl;
import com.lanit_tercom.library.data.manager.NetworkManager;
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ChannelListFragment extends BaseFragment implements ChannelListView {

    ChannelListPresenter channelListPresenter;

    RecyclerView channelListRecyclerView;
    ConstraintLayout constraintLayout;
    ChannelListAdapter channelListAdapter;

    ChannelRecyclerTouchListener touchListener;





    public ChannelListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_list, container, false);

        channelListPresenter.setView(this);
        initEmptyChannelList(view);
        initRecycleView(view);
        initializeListener();

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

        AuthManager authManager = new AuthManagerFirebaseImpl();
        NetworkManager networkManager = new NetworkManagerImpl(getContext());
        ChannelEntityDtoMapper dtoMapper = new ChannelEntityDtoMapper();
        UserEntityDtoMapper userEntityDtoMapper = new UserEntityDtoMapper();

        ChannelCache channelCache = new ChannelCache() {
            @Override
            public void saveChannel(String messageId, ChannelEntity channelEntity) {

            }
        };

        ChannelEntityStoreFactory channelEntityStoreFactory = new ChannelEntityStoreFactory(networkManager, channelCache);
        ChannelRepositoryImpl channelRepository = ChannelRepositoryImpl.getInstance(channelEntityStoreFactory, dtoMapper);

        UserEntityStoreFactory userEntityStoreFactory = new UserEntityStoreFactory(networkManager);
        UserRepositoryImpl userRepository = UserRepositoryImpl.getInstance(userEntityStoreFactory, userEntityDtoMapper);

        GetChannelsUseCase getChannelsUseCase = new GetChannelsUseCaseImpl(channelRepository, threadExecutor, postExecutionThread);
        AddChannelUseCase addChannelUseCase = new AddChannelUseCaseImpl(channelRepository, threadExecutor, postExecutionThread);
        EditChannelUseCase editChannelUseCase = new EditChannelUseCaseImpl(channelRepository, threadExecutor, postExecutionThread);
        DeleteChannelUseCase deleteChannelUseCase = new DeleteChannelUseCaseImpl(channelRepository, threadExecutor, postExecutionThread);

        GetUsersByIdUseCase getUsersByIdUseCase = new GetUsersByIdUseCaseImpl(userRepository, threadExecutor, postExecutionThread);

        channelListPresenter = new ChannelListPresenter(authManager,
                getChannelsUseCase,
                addChannelUseCase,
                editChannelUseCase,
                deleteChannelUseCase,
                getUsersByIdUseCase);

        channelListPresenter.refreshChannelsData();
    }


    private void initializeListener(){
        touchListener = new ChannelRecyclerTouchListener(this,channelListRecyclerView);
        touchListener
                .setClickable(new ChannelRecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        //Toast.makeText(getContext(), "Row was clicked", Toast.LENGTH_SHORT).show();
                        channelListAdapter.navigateToChat(position);
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {

                    }
                })
                .setSwipeOptionViews(R.id.delete_button,R.id.turnOffNotification_button, R.id.pin_message_button)
                .setSwipeable(R.id.rowFB, R.id.rowBG, new ChannelRecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        ChannelModel channelModel = channelListAdapter.getChannelByID(position);
                        switch (viewID){
                            case R.id.delete_button:
                                //ChannelModel channelModel = channelListAdapter.getChannelByID(position);
                                channelListPresenter.deleteChannel(channelModel);
                                Toast.makeText(getContext(), "Диалог удален", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.turnOffNotification_button:
                                turnOnOffNotification(channelModel);
                                break;
                            case R.id.pin_message_button:
                                pinChannel(channelModel);
                                break;

                        }
                    }
                });
        channelListRecyclerView.addOnItemTouchListener(touchListener);
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
        if (channelListPresenter.isChannelListEmpty()){
            constraintLayout.setVisibility(View.VISIBLE);
            channelListRecyclerView.setVisibility(View.INVISIBLE);
        }else{
            constraintLayout.setVisibility(View.INVISIBLE);
            channelListRecyclerView.setVisibility(View.VISIBLE);

        channelListAdapter.setChannels(channels);

        }
    }

    @Override
    public void renderChannelMembers(List<UserModel> channelMembers) {
        channelListAdapter.setChannelMembers(channelMembers);
    }

    private void initEmptyChannelList(View view){
        constraintLayout = view.findViewById(R.id.empty_channellist_layout);
    }

    private void turnOnOffNotification(ChannelModel channelModel){
        if (!channelModel.isNotification()) {
            channelModel.setOffNotification(true);
            Toast.makeText(getContext(),"Уведомления выключены",Toast.LENGTH_SHORT).show();
        } else {
            channelModel.setOffNotification(false);
            Toast.makeText(getContext(),"Уведомления включены",Toast.LENGTH_SHORT).show();
        }
        channelListPresenter.editChannel(channelModel);
    }

    private void pinChannel(ChannelModel channelModel){
        if (!channelModel.isPinned()){
            channelModel.setPinned(true);
            Toast.makeText(getContext(), "Сообщение закреплено", Toast.LENGTH_LONG).show();
        } else {
            channelModel.setPinned(false);
            Toast.makeText(getContext(), "Сообщение откреплено", Toast.LENGTH_LONG).show();
        }
        channelListPresenter.editChannel(channelModel);

    }

}