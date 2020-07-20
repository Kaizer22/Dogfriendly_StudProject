package com.lanit_tercom.domain.interactor.message.impl;

import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.message.GetMessagesUseCase;
import com.lanit_tercom.domain.interactor.message.UseCase;
import com.lanit_tercom.domain.repository.MessageRepository;


import java.util.List;

public class GetMessagesUseCaseImpl extends UseCase implements GetMessagesUseCase {
    private String peerId = "";
    private GetMessagesUseCase.Callback callback;

    public GetMessagesUseCaseImpl(MessageRepository messageRepository,
                                  ThreadExecutor threadExecutor,
                                  PostExecutionThread postExecutionThread) {
        super(messageRepository, threadExecutor, postExecutionThread);
    }

    @Override
    public void execute(String peerId, Callback callback) {
        if (peerId.isEmpty() || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.callback = callback;
        this.peerId = peerId;
    }

    @Override
    public void run() {
        this.messageRepository.getMessages(this.peerId, this.repositoryCallback);
    }

    private final MessageRepository.MessagesDetailCallback repositoryCallback =
            new MessageRepository.MessagesDetailCallback() {
                @Override
                public void onMessagesLoaded(List<MessageDto> messages) {
                    notifyGetMessagesSuccessfully(messages);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };


    private void notifyGetMessagesSuccessfully(final List<MessageDto> messages) {
        this.postExecutionThread.post(() -> callback.onMessagesDataLoaded(messages));
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }
}

