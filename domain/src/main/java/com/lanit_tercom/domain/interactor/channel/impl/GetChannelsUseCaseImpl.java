package com.lanit_tercom.domain.interactor.channel.impl;


import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.channel.GetChannelsUseCase;
import com.lanit_tercom.domain.interactor.channel.UseCase;
import com.lanit_tercom.domain.repository.ChannelRepository;

import java.util.List;

/**
 * @author nikolaygorokhov1@gmail.com
 */
public class GetChannelsUseCaseImpl extends UseCase implements GetChannelsUseCase {
    private String channelId;
    private GetChannelsUseCase.Callback callback;

    public GetChannelsUseCaseImpl(ChannelRepository channelRepository,
                                     ThreadExecutor threadExecutor,
                                     PostExecutionThread postExecutionThread) {
        super(channelRepository, threadExecutor, postExecutionThread);
    }

    @Override
    public void execute(String channelId, Callback callback) {
        if (channelId.isEmpty() || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.callback = callback;
        this.channelId = channelId;
    }

    @Override
    public void run() {
        this.channelRepository.getChannels(channelId, repositoryCallback);
    }

    private final ChannelRepository.ChannelsLoadCallback repositoryCallback =
            new ChannelRepository.ChannelsLoadCallback() {

                @Override
                public void onChannelsLoaded(List<ChannelDto> channels) {
                    notifyLoadChannelsSuccessfully(channels);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };


    private void notifyLoadChannelsSuccessfully(List<ChannelDto> channels) {
        this.postExecutionThread.post(() -> callback.onChannelsLoaded(channels));
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }

}
