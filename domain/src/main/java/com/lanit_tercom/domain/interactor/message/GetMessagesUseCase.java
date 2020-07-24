package com.lanit_tercom.domain.interactor.message;


import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import java.util.List;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public interface GetMessagesUseCase {

    interface Callback {
        void onMessagesDataLoaded(List<MessageDto> messages);

        void onError(ErrorBundle errorBundle);
    }

    void execute(String channelId, Callback callback);
}
