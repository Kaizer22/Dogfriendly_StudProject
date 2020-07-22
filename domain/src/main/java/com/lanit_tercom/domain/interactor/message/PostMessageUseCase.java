package com.lanit_tercom.domain.interactor.message;

import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public interface PostMessageUseCase {

    interface Callback {
        void onMessagePosted();

        void onError(ErrorBundle errorBundle);
    }

    void execute(MessageDto messageDto, Callback callback);
}
