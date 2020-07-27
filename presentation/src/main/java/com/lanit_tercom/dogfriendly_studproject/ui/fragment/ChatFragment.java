package com.lanit_tercom.dogfriendly_studproject.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.entity.MessageEntity;
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.MessageCache;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.message.MessageEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.MessageEntityDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.data.repository.MessageRepositoryImpl;
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.MessageProviderTemp;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.ChatPresenter;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.ChatView;
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.MessageAdapter;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.message.DeleteMessageUseCase;
import com.lanit_tercom.domain.interactor.message.EditMessageUseCase;
import com.lanit_tercom.domain.interactor.message.GetMessagesUseCase;
import com.lanit_tercom.domain.interactor.message.PostMessageUseCase;
import com.lanit_tercom.domain.interactor.message.impl.DeleteMessageUseCaseImpl;
import com.lanit_tercom.domain.interactor.message.impl.EditMessageUseCaseImpl;
import com.lanit_tercom.domain.interactor.message.impl.GetMessagesUseCaseImpl;
import com.lanit_tercom.domain.interactor.message.impl.PostMessageUseCaseImpl;
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase;
import com.lanit_tercom.domain.interactor.user.impl.GetUserDetailsUseCaseImpl;
import com.lanit_tercom.domain.repository.MessageRepository;
import com.lanit_tercom.library.data.manager.NetworkManager;
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *  Фрагмент, отвечающий за отображение диалога между двумя пользователями
 *  @author dshebut@rambler.ru
 */
public class ChatFragment extends BaseFragment implements ChatView {

    ChatPresenter chatPresenter;

    List<MessageModel> messages;
    RecyclerView chat;
    MessageAdapter messageAdapter;
    //Временно
    String channelID = "-MCqwIrhuEPqkgz1GV18";

    //region Implementations
    @Override
    public void initializePresenter() {
        //Тестовый код
        //authManager = new AuthManagerFirebaseImpl();
        //chatPresenter = new ChatPresenter(authManager);
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        AuthManager authManager = new AuthManagerFirebaseImpl();
        NetworkManager networkManager = new NetworkManagerImpl(getContext());
        MessageEntityDtoMapper messageEntityDtoMapper = new MessageEntityDtoMapper();
        MessageCache messageCache = new MessageCache() {
            @Override
            public void saveMessage(String messageId, MessageEntity messageEntity) {
                //TODO что здесь должно быть?
            }
        };
        MessageEntityStoreFactory messageEntityStoreFactory =
                new MessageEntityStoreFactory(networkManager, messageCache);
        MessageRepository messageRepository = MessageRepositoryImpl
                .getInstance(messageEntityStoreFactory, messageEntityDtoMapper);

        DeleteMessageUseCase deleteMessageUseCase =
                new DeleteMessageUseCaseImpl(messageRepository, threadExecutor, postExecutionThread);
        EditMessageUseCase editMessageUseCase =
                new EditMessageUseCaseImpl(messageRepository, threadExecutor, postExecutionThread);
        GetMessagesUseCase getMessagesUseCase =
                new GetMessagesUseCaseImpl(messageRepository, threadExecutor, postExecutionThread);
        PostMessageUseCase postMessageUseCase =
                new PostMessageUseCaseImpl(messageRepository, threadExecutor, postExecutionThread);

        //GetUserDetailsUseCase getUserDetailsUseCase = new GetUserDetailsUseCaseImpl();

        chatPresenter = new ChatPresenter(channelID, authManager,
                deleteMessageUseCase, editMessageUseCase,
                getMessagesUseCase, postMessageUseCase);
        chatPresenter.setView(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_chat, container, false);


        //Тестовый код
        //authManager.signInEmail("dshebut@rambler.ru", "123456",
                //new AuthManager.SignInCallback() {
                   // @Override
                    //public void OnSignInFinished(String currentUserID) {
                        //Log.i("USER_CHAT", currentUserID);
                    //}

                    //@Override
                   // public void OnError(Exception e) {
                       // e.printStackTrace();
                    //}
                //});

        showLoading();

        initRecyclerView(root);
        chatPresenter.refreshData();
        initInteractions(root);

        return root;
    }


    @Override
    public void showProgressMessage(@NotNull String event) {
        //Пока отобразим только тост
        Toast.makeText(getContext(), event, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void showLoading() {
        //TODO отображение загрузки в UI
    }

    @Override
    public void hideLoading() {
        //TODO скрытие загрузки в UI
    }

    @Override
    public void showError(@NotNull String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void refreshRecyclerView() {
        messageAdapter.notifyDataSetChanged();
        chat.smoothScrollToPosition(
                messageAdapter.getItemCount());
        chat.invalidate();
    }

    private void initInteractions(@NotNull View root){
        EditText messageText = root.findViewById(R.id.edit_text_send_message);

        ImageButton sendMessage = root.findViewById(R.id.button_send_message);
        sendMessage.setOnClickListener(v -> {
            sendMessage(messageText);
        });

        ImageButton backToDialogs = root.findViewById(R.id.button_back);
        backToDialogs.setOnClickListener(v -> {
            backToDialogsFragment();
        });
    }

    private void initRecyclerView(@NotNull View root){
        chat = root.findViewById(R.id.chat);
        chat.setLayoutManager(new LinearLayoutManager(getActivity()));
        messageAdapter = new MessageAdapter(getContext(),
                messages, chatPresenter.getCurrentUserID());
        chat.setAdapter(messageAdapter);
    }
    //endregion

    //region Actions
    private void sendMessage(@NotNull EditText messageText){
        chatPresenter.sendMessage(messageText
                .getText().toString());
        messageText.setText(R.string.new_message);
    }

    private void backToDialogsFragment(){
        //TODO возвращение к экрану диалогов с помощью Navigator
    }

    //endregion


}
