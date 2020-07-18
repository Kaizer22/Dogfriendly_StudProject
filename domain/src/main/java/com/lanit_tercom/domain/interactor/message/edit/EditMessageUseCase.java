package com.lanit_tercom.domain.interactor.message.edit;

import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public interface EditMessageUseCase {

    interface Callback {
        void onMessageEdited(MessageDto messageDto);

        void onError(ErrorBundle errorBundle);
    }

    void execute(MessageDto messageDto, Callback callback);
}
