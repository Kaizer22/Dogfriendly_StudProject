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
import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.MessageProviderTemp;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.ChatPresenter;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.ChatView;
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.MessageAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *  Фрагмент, отвечающий за отображение диалога между двумя пользователями
 *  @author dshebut@rambler.ru
 */
public class ChatFragment extends BaseFragment implements ChatView {

    private final String SENDING_MESSAGE_EVENT = "Отправка сообщения...";

    ChatPresenter chatPresenter;
    AuthManager authManager;

    List<MessageModel> messages;
    RecyclerView chat;
    MessageAdapter messageAdapter;
    @Override
    public void initializePresenter() {
        //Тестовый код
        authManager = new AuthManagerFirebaseImpl();
        chatPresenter = new ChatPresenter(authManager);

        //chatPresenter = new ChatPresenter(new AuthManagerFirebaseImpl());
        chatPresenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_chat, container, false);

        //Тестовый код
        authManager.signInEmail("dshebut@rambler.ru", "123456",
                new AuthManager.SignInCallback() {
                    @Override
                    public void OnSignInFinished(String currentUserID) {
                        Log.i("USER_CHAT", currentUserID);
                    }

                    @Override
                    public void OnError(Exception e) {
                        e.printStackTrace();
                    }
                });

        initData();
        initRecyclerView(root);

        initInteractions(root);
        return root;
    }



    private void initData(){
        MessageProviderTemp.initProvider();
        messages = MessageProviderTemp.getMessages();
    }

    private void initInteractions(View root){
        EditText messageText = root.findViewById(R.id.edit_text_send_message);

        ImageButton sendMessage = root.findViewById(R.id.button_send_message);
        sendMessage.setOnClickListener(v -> {
            //TODO изменить эту часть кода
            chatPresenter.sendMessage(messageText
                    .getText().toString());
            showProgressMessage(SENDING_MESSAGE_EVENT);
            messageAdapter.notifyDataSetChanged();
            chat.smoothScrollToPosition(
                    messageAdapter.getItemCount());
            messageText.setText(R.string.new_message);
        });


        ImageButton backToDialogs = root.findViewById(R.id.button_back);
        backToDialogs.setOnClickListener(v -> {
            //TODO возвращение к списку диалогов
        });
    }

    private void initRecyclerView(View root){
        chat = root.findViewById(R.id.chat);
        chat.setLayoutManager(new LinearLayoutManager(getActivity()));
        messageAdapter = new MessageAdapter(getContext(),
                messages, chatPresenter.getCurrentUserID());
        chat.setAdapter(messageAdapter);
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
}
