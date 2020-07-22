package com.lanit_tercom.domain.interactor.get;

import com.lanit_tercom.domain.dto.ChannelDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.UseCase;
import com.lanit_tercom.domain.repository.ChannelRepository;
import com.lanit_tercom.domain.repository.UserRepository;

public class GetChannelListUseCaseImpl implements GetChannelListUseCase {

    private final ChannelRepository channelRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private String channelId = "";
    private GetChannelListUseCase.Callback callback;


    public GetChannelListUseCaseImpl(ChannelRepository channelRepository,
                                        ThreadExecutor threadExecutor,
                                        PostExecutionThread postExecutionThread) {
        if (channelRepository == null || threadExecutor == null || postExecutionThread == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }

        this.channelRepository = channelRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void run() {
        this.channelRepository.getChannelByID(this.channelId, this.repositoryCallback);
    }

    @Override
    public void execute(String channelId, Callback callback) {
        if(channelId.isEmpty() || callback == null){
            throw new IllegalArgumentException("Invalid parameter");
        }
       // super(execute);
        this.channelId = channelId;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }


    private final ChannelRepository.ChannelDetailsCallback repositoryCallback =
            new ChannelRepository.ChannelDetailsCallback() {
                @Override
                public void onChannelLoaded(ChannelDto channelDto) {
                    notifyGetChannelDetailsSuccessfully(channelDto);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyGetChannelDetailsSuccessfully (final ChannelDto channelDto){
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onChannelDataLoaded(channelDto);
            }
        });
    }

    private void notifyError(final ErrorBundle errorBundle){
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(errorBundle);
            }
        });
    }
}
