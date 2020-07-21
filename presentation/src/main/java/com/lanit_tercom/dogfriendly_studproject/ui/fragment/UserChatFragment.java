package com.lanit_tercom.dogfriendly_studproject.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lanit_tercom.data.auth_manager.AuthManager;
import com.lanit_tercom.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.MessageModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.MessageProviderTemp;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserChatPresenter;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserChatView;
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.MessageAdapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *  Фрагмент, отвечающий за отображение диалога между двумя пользователями
 *  @author dshebut@rambler.ru
 */
public class UserChatFragment extends BaseFragment implements UserChatView {

    private final String SENDING_MESSAGE_EVENT = "Отправка сообщения...";

    UserChatPresenter userChatPresenter;
    AuthManager authManager;

    List<MessageModel> messages;
    RecyclerView chat;
    MessageAdapter messageAdapter;
    @Override
    public void initializePresenter() {
        //TODO разобраться с использованием authManager в presentation слое
        authManager = new AuthManagerFirebaseImpl();
        userChatPresenter = new UserChatPresenter(authManager);
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

    @Override
    public void showProgressMessage(String event) {

    }

    private void initData(){
        MessageProviderTemp.initProvider();
        messages = MessageProviderTemp.getMessages();
        //Collections.reverse(messages);
    }

    private void initInteractions(View root){
        EditText messageText = root.findViewById(R.id.edit_text_send_message);

        ImageButton sendMessage = root.findViewById(R.id.button_send_message);
        sendMessage.setOnClickListener(v -> {
            userChatPresenter.sendMessage(messageText
                    .getText().toString());
            //initData();
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
                messages, userChatPresenter.getCurrentUserID());
        chat.setAdapter(messageAdapter);
    }
}
