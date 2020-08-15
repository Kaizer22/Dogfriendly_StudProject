package com.lanit_tercom.dogfriendly_studproject.data.firebase.walk;

import com.lanit_tercom.dogfriendly_studproject.data.entity.WalkEntity;
import com.lanit_tercom.domain.exception.ErrorBundle;

public interface WalkEntityStore {

    interface Error{
        void onError(ErrorBundle errorBundle);
    }

    interface GetWalkCallback extends Error{
        void onWalkLoaded(WalkEntity walkEntity);
    }

    interface AddWalkCallback extends Error{
        void onWalkAdded();
    }

    interface EditWalkCallback extends Error{
        void onWalkEdited();
    }

    interface  DeleteWalkCallback extends Error{
        void onWalkDeleted();
    }

    void getWalk(String userId, GetWalkCallback getWalkCallback);

    void addWalk(WalkEntity walkEntity, AddWalkCallback addWalkCallback);

    void editWalk(WalkEntity walkEntity, EditWalkCallback editWalkCallback);

    void deleteWalk(WalkEntity walkEntity, DeleteWalkCallback deleteWalkCallback);

}
