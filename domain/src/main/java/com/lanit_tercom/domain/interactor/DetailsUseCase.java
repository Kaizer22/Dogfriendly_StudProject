package com.lanit_tercom.domain.interactor;

import com.lanit_tercom.domain.repository.Callback;

public interface DetailsUseCase extends Interactor {

    void perform(Callback callback);
}
