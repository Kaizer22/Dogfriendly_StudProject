package com.lanit_tercom.domain.interactor.message.impl;

import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.message.EditMessageUseCase;
import com.lanit_tercom.domain.interactor.message.UseCase;
import com.lanit_tercom.domain.repository.MessageRepository;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public class EditMessageUseCaseImpl extends UseCase implements EditMessageUseCase {
    private MessageDto messageDto = null;
    private EditMessageUseCase.Callback callback;

    public EditMessageUseCaseImpl(MessageRepository messageRepository,
                                    ThreadExecutor threadExecutor,
                                    PostExecutionThread postExecutionThread) {
        super(messageRepository, threadExecutor, postExecutionThread);
    }

    @Override
    public void execute(MessageDto messageDto, Callback callback) {
        if (messageDto == null || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.callback = callback;
        this.messageDto = messageDto;
    }

    @Override
    public void run() {
        this.messageRepository.editMessage(this.messageDto, this.repositoryCallback);
    }

    private final MessageRepository.MessageEditCallback repositoryCallback =
            new MessageRepository.MessageEditCallback() {

                @Override
                public void onMessageEdited() {
                    notifyEditMessageSuccessfully();
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };


    private void notifyEditMessageSuccessfully() {
        this.postExecutionThread.post(() -> callback.onMessageEdited());
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }
}
