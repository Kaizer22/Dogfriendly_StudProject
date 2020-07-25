package com.lanit_tercom.dogfriendly_studproject.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.ChannelEntityDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.data.repository.ChannelRepositoryImpl;
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread;
import com.lanit_tercom.dogfriendly_studproject.mapper.ChannelModelDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.ChannelModel;
import com.lanit_tercom.dogfriendly_studproject.ui.activity.ChannelListActivity;
import com.lanit_tercom.dogfriendly_studproject.ui.viewholder.ChannelListViewHolder;
import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.channel.GetChannelsUseCase;
import com.lanit_tercom.domain.interactor.channel.impl.GetChannelsUseCaseImpl;
import com.lanit_tercom.domain.repository.ChannelRepository;
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl;

import java.util.ArrayList;
import java.util.List;

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListViewHolder> {

    private LayoutInflater inflater;
    private List<ChannelModel> channels;
    private String currentUserID;

    public ChannelListAdapter(Context context){ //  List<ChannelModel> channels
        final List<ChannelModel> channelsModel = new ArrayList<>();

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        NetworkManagerImpl networkManager = new NetworkManagerImpl(context);
        ChannelEntityStoreFactory channelEntityStoreFactory = new ChannelEntityStoreFactory(networkManager, null);
        ChannelEntityDtoMapper dtoMapper = new ChannelEntityDtoMapper();
        ChannelRepositoryImpl channelRepository = new ChannelRepositoryImpl(channelEntityStoreFactory, dtoMapper);

        final ChannelModelDtoMapper modelDtoMapper = new ChannelModelDtoMapper();

        GetChannelsUseCase channelsUseCase = new GetChannelsUseCaseImpl(channelRepository, threadExecutor, postExecutionThread);

        GetChannelsUseCase.Callback getChannelsUseCase = new GetChannelsUseCase.Callback() {
            @Override
            public void onChannelsLoaded(List<ChannelDto> channelsDto) {
                for (ChannelDto dtoObject: channelsDto){
                    channelsModel.add(modelDtoMapper.mapToModel(dtoObject));
                }
                Log.i("TEST_Channels", "Success");
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                Log.i("TEST_Channels", "error");
            }
        };

        /**
            При вызове execute c id (id канала) -MCxNrG1TEk0XoOdN7X8 появляется слудюущая ошибка:
         Can't convert object of type java.util.ArrayList to type com.lanit_tercom.dogfriendly_studproject.data.entity.ChannelEntity

         Если брать id пользователя, то ошибка такая:
         java.lang.BootstrapMethodError: Exception from call site #5 bootstrap method
         */

        channelsUseCase.execute("1OUgqDel92RLeacUFajLizVxWyk2", getChannelsUseCase);

        //1OUgqDel92RLeacUFajLizVxWyk2 -- id user
        //-MCxNrG1TEk0XoOdN7X8 -- id channel


        this.channels = channelsModel;
        inflater = LayoutInflater.from(context);
        Log.i("TEST_Channels", "Adapter");
        //currentUserID = authManager.getCurrentUserId();
    }

    @NonNull
    @Override
    public ChannelListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_channel_item_layout, parent, false);
        return new ChannelListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelListViewHolder holder, int position) {
        ChannelModel channelModel = channels.get(position);
        // TODO Change image provider
        holder.setUserProfileImage(R.drawable.ic_user_profile_image);
        // TODO Change ID to Name
        holder.setUserReceiverName(channelModel.getLastMessageOwner());
        holder.setLastMessage(channelModel.getLastMessage());
        holder.setLastMessageTime(channelModel.getTimestamp().toString());
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }
}
