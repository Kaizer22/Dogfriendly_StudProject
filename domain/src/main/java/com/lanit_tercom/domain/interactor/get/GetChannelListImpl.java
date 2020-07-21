package com.lanit_tercom.domain.interactor.get;

import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.UseCase;
import com.lanit_tercom.domain.repository.UserRepository;

public class GetChannelListImpl extends UseCase implements GetChannelList {

    private String channelId = "";
    private GetChannelList.Callback callback;


    protected GetChannelListImpl(UserRepository userRepository,
                                 ThreadExecutor threadExecutor,
                                 PostExecutionThread postExecutionThread) {
        super(userRepository, threadExecutor, postExecutionThread);
    }

    @Override
    public void run() {
        /**
         * ?????
         */
    }

    @Override
    public void execute(String channelId, Callback callback) {
        if(channelId.isEmpty() || callback == null){
            throw new IllegalArgumentException("Invalid parameter");
        }
        super.execute();
        this.channelId = channelId;
        this.callback = callback;
    }
}
