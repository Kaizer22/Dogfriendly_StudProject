package com.lanit_tercom.domain.interactor.message;

import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.List;

public interface GetLastMessagesUseCase {

    interface Callback{
        void onLastMessagesLoaded(List<MessageDto> messages);

        void onError(ErrorBundle errorBundle);
    }

    void execute(List<String> channelsId, Callback callback);
}
