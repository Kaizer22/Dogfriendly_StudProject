package com.lanit_tercom.domain.interactor.walk.impl;

import com.lanit_tercom.domain.dto.WalkDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.walk.GetWalkUseCase;
import com.lanit_tercom.domain.interactor.walk.UseCase;
import com.lanit_tercom.domain.repository.WalkRepository;

public class GetWalkUseCaseImpl extends UseCase implements GetWalkUseCase {

    private String userId;
    private GetWalkUseCase.Callback callback;


    protected GetWalkUseCaseImpl(WalkRepository walkRepository,
                                 ThreadExecutor threadExecutor,
                                 PostExecutionThread postExecutionThread) {
        super(walkRepository, threadExecutor, postExecutionThread);
    }

    @Override
    public void execute(String userId, Callback callback) {
        if (userId.isEmpty() || callback == null){
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.userId = userId;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.walkRepository.getWalkDetails(userId, walkDetailsCallback);
    }

    private WalkRepository.GetWalkDetailsCallback walkDetailsCallback =
            new WalkRepository.GetWalkDetailsCallback() {
                @Override
                public void onWalkLoaded(WalkDto walkDto) {
                    notifyGetWalkSuccessfully(walkDto);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyGetWalkSuccessfully(WalkDto walkDto){
        this.postExecutionThread.post(() -> callback.onWalkDataLoaded(walkDto));
    }

    private void notifyError(final ErrorBundle errorBundle){
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }
}
