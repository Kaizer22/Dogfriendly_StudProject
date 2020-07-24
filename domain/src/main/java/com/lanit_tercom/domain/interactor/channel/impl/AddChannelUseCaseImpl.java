package com.lanit_tercom.domain.interactor.channel.impl;

import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.channel.UseCase;
import com.lanit_tercom.domain.interactor.channel.AddChannelUseCase;
import com.lanit_tercom.domain.repository.ChannelRepository;

public class AddChannelUseCaseImpl extends UseCase implements AddChannelUseCase {
    private ChannelDto channelDto;
    private AddChannelUseCase.Callback callback;

    public AddChannelUseCaseImpl(ChannelRepository channelRepository,
                                 ThreadExecutor threadExecutor,
                                 PostExecutionThread postExecutionThread) {
        super(channelRepository, threadExecutor, postExecutionThread);
    }

    @Override
    public void execute(ChannelDto channelDto, Callback callback) {
        if (channelDto == null || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.callback = callback;
        this.channelDto = channelDto;
    }

    @Override
    public void run() {
        this.channelRepository.addChannel(channelDto, repositoryCallback);
    }

    private final ChannelRepository.ChannelAddCallback repositoryCallback =
            new ChannelRepository.ChannelAddCallback() {

                @Override
                public void onChannelAdded() {
                    notifyAddChannelSuccessfully();
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };


    private void notifyAddChannelSuccessfully() {
        this.postExecutionThread.post(() -> callback.onChannelAdded());
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }

}

