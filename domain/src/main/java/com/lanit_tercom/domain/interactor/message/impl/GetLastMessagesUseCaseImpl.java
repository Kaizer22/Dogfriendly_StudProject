package com.lanit_tercom.domain.interactor.message.impl;

import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.message.GetLastMessagesUseCase;
import com.lanit_tercom.domain.interactor.message.UseCase;
import com.lanit_tercom.domain.repository.MessageRepository;

import java.util.List;

public class GetLastMessagesUseCaseImpl extends UseCase implements GetLastMessagesUseCase {

    private List<String> channelsId;
    private GetLastMessagesUseCase.Callback callback;

    public GetLastMessagesUseCaseImpl(MessageRepository messageRepository,
                                      ThreadExecutor threadExecutor,
                                      PostExecutionThread postExecutionThread){
        super(messageRepository, threadExecutor, postExecutionThread);
    }

    @Override
    public void execute(List<String> channelIdList, Callback callback) {
        if (channelIdList == null || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.callback = callback;
        this.channelsId = channelIdList;
    }

    @Override
    public void run() {
        this.messageRepository.getLastMessages(channelsId, repositoryCallback);
    }

    private final MessageRepository.LastMessagesDetailCallback repositoryCallback =
            new MessageRepository.LastMessagesDetailCallback() {
                @Override
                public void onLastMessagesLoaded(List<MessageDto> lastMessages) {
                    notifyGetMessagesSuccessfully(lastMessages);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyGetMessagesSuccessfully(final List<MessageDto> messages) {
        this.postExecutionThread.post(() -> callback.onLastMessagesLoaded(messages));
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }
}