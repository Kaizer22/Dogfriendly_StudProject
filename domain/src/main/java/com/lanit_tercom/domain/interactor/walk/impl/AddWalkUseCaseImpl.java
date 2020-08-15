package com.lanit_tercom.domain.interactor.walk.impl;

import com.lanit_tercom.domain.dto.WalkDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.walk.AddWalkUseCase;
import com.lanit_tercom.domain.interactor.walk.UseCase;
import com.lanit_tercom.domain.repository.WalkRepository;

public class AddWalkUseCaseImpl extends UseCase implements AddWalkUseCase {

    protected WalkDto walkDto;
    protected AddWalkUseCase.Callback callback;

    protected AddWalkUseCaseImpl(WalkRepository walkRepository,
                                 ThreadExecutor threadExecutor,
                                 PostExecutionThread postExecutionThread) {
        super(walkRepository, threadExecutor, postExecutionThread);
    }

    @Override
    public void execute(WalkDto walkDto, Callback callback) {
        if (walkDto == null || callback == null){
            throw new IllegalArgumentException("Invalid parameter!!!");
        }

        super.execute();
        this.walkDto = walkDto;
        this.callback = callback;

    }

    @Override
    public void run() {
        this.walkRepository.addWalk(walkDto, walkAddCallback);
    }

    private final WalkRepository.WalkAddCallback walkAddCallback =
            new WalkRepository.WalkAddCallback() {
                @Override
                public void onWalkAdded() {
                    notifyAddWalkSuccessfully();
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyAddWalkSuccessfully(){
        this.postExecutionThread.post(() -> callback.onWalkAdded());
    }

    private void notifyError(final ErrorBundle errorBundle){
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }
}
