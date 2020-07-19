package com.lanit_tercom.domain.interactor.message.post;

import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public interface PostMessageUseCase {

    interface Callback {
        void onMessagePosted(MessageDto MessageDto);

        void onError(ErrorBundle errorBundle);
    }

    void execute(MessageDto messageDto, Callback callback);
}
