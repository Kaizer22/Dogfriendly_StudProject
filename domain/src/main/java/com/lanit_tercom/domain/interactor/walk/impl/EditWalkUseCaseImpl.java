package com.lanit_tercom.domain.interactor.walk.impl;

import com.lanit_tercom.domain.dto.WalkDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.walk.EditWalkUseCase;
import com.lanit_tercom.domain.interactor.walk.UseCase;
import com.lanit_tercom.domain.repository.WalkRepository;

public class EditWalkUseCaseImpl extends UseCase implements EditWalkUseCase {

    private WalkDto walkDto;
    private EditWalkUseCase.Callback callback;

    public EditWalkUseCaseImpl(WalkRepository walkRepository,
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
        this.walkRepository.editWalk(walkDto, editCallback);
    }

    private final WalkRepository.WalkEditCallback editCallback
            = new WalkRepository.WalkEditCallback() {
        @Override
        public void onWalkEdited() {
            notifyEditWalkSuccessfully();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyError(errorBundle);
        }
    };

    private void notifyEditWalkSuccessfully(){
        this.postExecutionThread.post(() -> callback.onWalkEdited());
    }

    private void notifyError(final ErrorBundle errorBundle){
        this.postExecutionThread.post(() -> callback.onError(errorBundle));
    }
}
