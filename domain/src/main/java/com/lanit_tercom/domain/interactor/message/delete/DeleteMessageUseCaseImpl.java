package com.lanit_tercom.domain.interactor.message.delete;

import com.lanit_tercom.domain.dto.MessageDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.message.UseCase;
import com.lanit_tercom.domain.repository.MessageRepository;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public class DeleteMessageUseCaseImpl extends UseCase implements DeleteMessageUseCase{
    private MessageDto messageDto = null;
    private DeleteMessageUseCase.Callback callback;

    public DeleteMessageUseCaseImpl(MessageRepository messageRepository,
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
        this.messageRepository.deleteMessage(this.messageDto, this.repositoryCallback);
    }

    private final MessageRepository.MessageDetailCallback repositoryCallback =
            new MessageRepository.MessageDetailCallback() {

                @Override
                public void onMessageLoaded(MessageDto messageDto) {
                    notifyDeleteMessageSuccessfully(messageDto);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };


    private void notifyDeleteMessageSuccessfully(final MessageDto messageDto) {
        this.postExecutionThread.post(() -> callback.onMessageDeleted(messageDto));
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }

}
