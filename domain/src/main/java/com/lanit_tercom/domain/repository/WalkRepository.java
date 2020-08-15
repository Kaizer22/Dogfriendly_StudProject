package com.lanit_tercom.domain.repository;

import com.lanit_tercom.domain.dto.WalkDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

public interface WalkRepository {

    interface Error{
        void onError(ErrorBundle errorBundle);
    }

    interface GetWalkDetailsCallback extends Error{
        void onWalkLoaded(WalkDto walkDto);
    }

    interface WalkAddCallback extends Error{
        void onWalkAdded();
    }

    interface WalkEditCallback extends Error{
        void onWalkEdited();
    }

    interface WalkDeleteCallback extends Error{
        void onWalkDeleted();
    }

    void getWalkDetails(String userId, GetWalkDetailsCallback getWalkDetailsCallback);

    void addWalk(WalkDto walkDto, WalkAddCallback walkAddCallback);

    void editWalk(WalkDto walkDto, WalkEditCallback walkEditCallback);

    void deleteWalk(WalkDto walkDto, WalkDeleteCallback walkDeleteCallback);
}
