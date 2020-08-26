package com.lanit_tercom.dogfriendly_studproject.data.repository;

import com.lanit_tercom.dogfriendly_studproject.data.entity.ChannelEntity;
import com.lanit_tercom.dogfriendly_studproject.data.exception.ChannelListException;
import com.lanit_tercom.dogfriendly_studproject.data.exception.RepositoryErrorBundle;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStore;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.ChannelEntityDtoMapper;
import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.repository.ChannelRepository;

import java.util.List;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public class ChannelRepositoryImpl implements ChannelRepository {

    private static ChannelRepositoryImpl INSTANCE;

    public static synchronized ChannelRepositoryImpl getInstance(ChannelEntityStoreFactory channelEntityStoreFactory,
                                                                 ChannelEntityDtoMapper channelEntityDtoMapper) {
        if (INSTANCE == null) {
            INSTANCE = new ChannelRepositoryImpl(channelEntityStoreFactory, channelEntityDtoMapper);
        }
        return INSTANCE;
    }

    private final ChannelEntityStoreFactory channelEntityStoreFactory;
    private final ChannelEntityDtoMapper channelEntityDtoMapper;

    public ChannelRepositoryImpl(ChannelEntityStoreFactory channelEntityStoreFactory,
                                 ChannelEntityDtoMapper channelEntityDtoMapper) {

        if(channelEntityStoreFactory == null || channelEntityDtoMapper == null){
            throw new IllegalArgumentException("Invalid null parameters in constructor!!!");
        }

        this.channelEntityStoreFactory = channelEntityStoreFactory;
        this.channelEntityDtoMapper = channelEntityDtoMapper;
    }

    @Override
    public void getChannels(String userId, ChannelsLoadCallback callback) {
        ChannelEntityStore channelEntityStore = this.channelEntityStoreFactory.create();

        channelEntityStore.getChannels(userId, new ChannelEntityStore.GetChannelsCallback() {
            @Override
            public void onChannelsLoaded(List<ChannelEntity> channels) {
                List<ChannelDto> channelDtos = channelEntityDtoMapper.mapForList(channels);
                if (channelDtos != null) {
                    callback.onChannelsLoaded(channelDtos);
                } else {
                    callback.onError(new RepositoryErrorBundle(new ChannelListException()));
                }
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                callback.onError(errorBundle);
            }
        });
    }

    @Override
    public void addChannel(ChannelDto channelDto, ChannelAddCallback callback) {
        ChannelEntityStore channelEntityStore = this.channelEntityStoreFactory.create();
        ChannelEntity channelEntity = this.channelEntityDtoMapper.map1(channelDto);

        channelEntityStore.addChannel(channelEntity , new ChannelEntityStore.AddChannelCallback() {
            @Override
            public void onChannelAdded() {
                callback.onChannelAdded();
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                callback.onError(errorBundle);
            }
        });
    }

    @Override
    public void editChannel(ChannelDto channelDto, ChannelEditCallback callback) {
        ChannelEntityStore channelEntityStore = this.channelEntityStoreFactory.create();
        ChannelEntity channelEntity = this.channelEntityDtoMapper.map1(channelDto);

        channelEntityStore.editChannel(channelEntity, new ChannelEntityStore.EditChannelCallback() {
            @Override
            public void onChannelEdited() {
                callback.onChannelEdited();
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                callback.onError(errorBundle);
            }
        });
    }

    @Override
    public void deleteChannel(String userId, ChannelDto channelDto, ChannelDeleteCallback callback) {
        ChannelEntityStore channelEntityStore = this.channelEntityStoreFactory.create();
        ChannelEntity channelEntity = this.channelEntityDtoMapper.map1(channelDto);

        channelEntityStore.deleteChannel(userId, channelEntity, new ChannelEntityStore.DeleteChannelCallback() {
            @Override
            public void onChannelDeleted() {
                callback.onChannelDeleted();
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                callback.onError(errorBundle);
            }
        });


    }
}

