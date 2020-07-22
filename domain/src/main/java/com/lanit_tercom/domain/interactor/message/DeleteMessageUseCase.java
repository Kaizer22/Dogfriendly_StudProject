package com.lanit_tercom.domain.interactor.message;

import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public interface DeleteMessageUseCase {

    interface Callback {
        void onMessageDeleted();

        void onError(ErrorBundle errorBundle);
    }

    void execute(MessageDto messageDto, Callback callback);
}
