package com.lanit_tercom.domain.interactor.walk.impl;

import com.lanit_tercom.domain.dto.WalkDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.channel.DeleteChannelUseCase;
import com.lanit_tercom.domain.interactor.walk.DeleteWalkUseCase;
import com.lanit_tercom.domain.interactor.walk.UseCase;
import com.lanit_tercom.domain.repository.WalkRepository;

public class DeleteWalkUseCaseImpl extends UseCase implements DeleteWalkUseCase {

    private WalkDto walkDto;
    private DeleteChannelUseCase.Callback callback;

    public DeleteWalkUseCaseImpl(WalkRepository walkRepository,
                                    ThreadExecutor threadExecutor,
                                    PostExecutionThread postExecutionThread) {
        super(walkRepository, threadExecutor, postExecutionThread);
    }

    @Override
    public void execute(WalkDto walkDto, DeleteChannelUseCase.Callback callback) {
        if (walkDto == null || callback == null){
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        super.execute();
        this.walkDto = walkDto;
        this.callback = callback;
    }


    @Override
    public void run() {
        this.walkRepository.deleteWalk(walkDto, deleteCallback);
    }

    private final WalkRepository.WalkDeleteCallback deleteCallback =
            new WalkRepository.WalkDeleteCallback() {
                @Override
                public void onWalkDeleted() {
                    notifyDeleteWalkSuccessfully();
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyDeleteWalkSuccessfully(){
        this.postExecutionThread.post(() -> callback.onChannelDeleted());
    }

    private void notifyError(ErrorBundle errorBundle){
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }

}
