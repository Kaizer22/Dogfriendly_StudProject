package com.lanit_tercom.dogfriendly_studproject.data.repository;

import com.lanit_tercom.dogfriendly_studproject.data.entity.WalkEntity;
import com.lanit_tercom.dogfriendly_studproject.data.exception.RepositoryErrorBundle;
import com.lanit_tercom.dogfriendly_studproject.data.exception.WalkException;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.walk.WalkEntityStore;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.walk.WalkEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.WalkEntityDtoMapper;
import com.lanit_tercom.domain.dto.WalkDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.repository.WalkRepository;

public class WalkRepositoryImpl implements WalkRepository {

    private static WalkRepositoryImpl INSTANCE;

    private final WalkEntityStoreFactory walkEntityStoreFactory;
    private final WalkEntityDtoMapper walkEntityDtoMapper;

    public WalkRepositoryImpl(WalkEntityStoreFactory walkEntityStoreFactory,
                              WalkEntityDtoMapper walkEntityDtoMapper){

        if(walkEntityDtoMapper == null || walkEntityStoreFactory == null){
            throw new IllegalArgumentException("Invalid null parameters in constructor!!!");
        }

        this.walkEntityStoreFactory = walkEntityStoreFactory;
        this.walkEntityDtoMapper = walkEntityDtoMapper;
    }

    public static synchronized WalkRepositoryImpl getInstance(WalkEntityStoreFactory walkEntityStoreFactory,
                                                              WalkEntityDtoMapper walkEntityDtoMapper){
        if (INSTANCE == null){
            INSTANCE = new WalkRepositoryImpl(walkEntityStoreFactory, walkEntityDtoMapper);
        }
        return INSTANCE;
    }

    @Override
    public void getWalkDetails(String userId, GetWalkDetailsCallback getWalkDetailsCallback) {
        WalkEntityStore walkEntityStore = this.walkEntityStoreFactory.create();

        walkEntityStore.getWalk(userId, new WalkEntityStore.GetWalkCallback() {
            @Override
            public void onWalkLoaded(WalkEntity walkEntity) {
                WalkDto walkDto = walkEntityDtoMapper.mapEntityToDto(walkEntity);
                if (walkDto != null){
                    getWalkDetailsCallback.onWalkLoaded(walkDto);
                }
                else getWalkDetailsCallback.onError(new RepositoryErrorBundle(new WalkException()));
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                getWalkDetailsCallback.onError(errorBundle);
            }
        });
    }

    @Override
    public void addWalk(WalkDto walkDto, WalkAddCallback walkAddCallback) {
        WalkEntityStore walkEntityStore = this.walkEntityStoreFactory.create();
        WalkEntity walkEntity = this.walkEntityDtoMapper.mapDtoToEntity(walkDto);

        walkEntityStore.addWalk(walkEntity, new WalkEntityStore.AddWalkCallback() {
            @Override
            public void onWalkAdded() {
                walkAddCallback.onWalkAdded();
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                walkAddCallback.onError(errorBundle);
            }
        });
    }

    @Override
    public void editWalk(WalkDto walkDto, WalkEditCallback walkEditCallback) {
        WalkEntityStore walkEntityStore = this.walkEntityStoreFactory.create();
        WalkEntity walkEntity = this.walkEntityDtoMapper.mapDtoToEntity(walkDto);

        walkEntityStore.editWalk(walkEntity, new WalkEntityStore.EditWalkCallback() {
            @Override
            public void onWalkEdited() {
                walkEditCallback.onWalkEdited();
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                walkEditCallback.onError(errorBundle);
            }
        });
    }

    @Override
    public void deleteWalk(WalkDto walkDto, WalkDeleteCallback walkDeleteCallback) {
        WalkEntityStore walkEntityStore = this.walkEntityStoreFactory.create();
        WalkEntity walkEntity = this.walkEntityDtoMapper.mapDtoToEntity(walkDto);

        walkEntityStore.deleteWalk(walkEntity, new WalkEntityStore.DeleteWalkCallback() {
            @Override
            public void onWalkDeleted() {
                walkDeleteCallback.onWalkDeleted();
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                walkDeleteCallback.onError(errorBundle);
            }
        });
    }
}
