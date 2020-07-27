package com.lanit_tercom.domain.interactor.channel.impl;

import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.channel.DeleteChannelUseCase;
import com.lanit_tercom.domain.interactor.channel.UseCase;
import com.lanit_tercom.domain.repository.ChannelRepository;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public class DeleteChannelUseCaseImpl extends UseCase implements DeleteChannelUseCase {
    private String userId;
    private ChannelDto channelDto;
    private DeleteChannelUseCase.Callback callback;

    public DeleteChannelUseCaseImpl(ChannelRepository channelRepository,
                                    ThreadExecutor threadExecutor,
                                    PostExecutionThread postExecutionThread) {
        super(channelRepository, threadExecutor, postExecutionThread);
    }

    @Override
    public void execute(String userId, ChannelDto channelDto, Callback callback) {
        if (userId.isEmpty() || channelDto == null || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.userId = userId;
        this.callback = callback;
        this.channelDto = channelDto;
    }

    @Override
    public void run() {
        this.channelRepository.deleteChannel(userId, channelDto, repositoryCallback);
    }

    private final ChannelRepository.ChannelDeleteCallback repositoryCallback =
            new ChannelRepository.ChannelDeleteCallback() {

                @Override
                public void onChannelDeleted() {
                    notifyDeleteChannelSuccessfully();
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };


    private void notifyDeleteChannelSuccessfully() {
        this.postExecutionThread.post(() -> callback.onChannelDeleted());
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }

}
