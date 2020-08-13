package com.lanit_tercom.dogfriendly_studproject.mvp.view;

import com.lanit_tercom.dogfriendly_studproject.mvp.model.WalkModel;

public interface WalkDetailsView extends LoadDataView {

    void renderCurrentWalk(WalkModel walkModel);
}
