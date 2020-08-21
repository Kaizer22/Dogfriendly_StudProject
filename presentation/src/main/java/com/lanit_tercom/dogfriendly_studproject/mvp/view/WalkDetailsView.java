package com.lanit_tercom.dogfriendly_studproject.mvp.view;

import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.WalkModel;

import java.util.List;

public interface WalkDetailsView extends LoadDataView {

    void renderCurrentWalk(WalkModel walkModel);

    void renderWalkCreator(UserModel userModel);

    void renderWalkMembers(List<UserModel> members);
}
