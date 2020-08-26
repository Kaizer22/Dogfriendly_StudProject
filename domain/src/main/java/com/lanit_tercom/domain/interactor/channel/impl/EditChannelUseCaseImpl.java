package com.lanit_tercom.domain.interactor.channel.impl;

import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.channel.EditChannelUseCase;
import com.lanit_tercom.domain.interactor.channel.UseCase;
import com.lanit_tercom.domain.repository.ChannelRepository;

public class EditChannelUseCaseImpl extends UseCase implements EditChannelUseCase {
    private ChannelDto channelDto;
    private EditChannelUseCase.Callback callback;

    public EditChannelUseCaseImpl(ChannelRepository channelRepository,
                                     ThreadExecutor threadExecutor,
                                     PostExecutionThread postExecutionThread) {
        super(channelRepository, threadExecutor, postExecutionThread);
    }

    @Override
    public void run() { this.channelRepository.editChannel(channelDto, repositoryCallback);}

    private final ChannelRepository.ChannelEditCallback repositoryCallback =
            new ChannelRepository.ChannelEditCallback() {

                @Override
                public void onChannelEdited() {
                    notifyEditChannelSuccessfully();
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Override
    public void execute(ChannelDto channelDto, Callback callback) {
        if (channelDto == null || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.callback = callback;
        this.channelDto = channelDto;
    }

    private void notifyEditChannelSuccessfully() {
        this.postExecutionThread.post(() -> callback.onChannelEdited());
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }
}
