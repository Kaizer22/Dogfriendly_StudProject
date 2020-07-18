package com.lanit_tercom.dogfriendly_studproject.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    UserChatPresenter userChatPresenter;
    @Override
    public void initializePresenter() {
        //TODO разобраться с использованием authManager в presentation слое
        AuthManager authManager = new AuthManagerFirebaseImpl();
        userChatPresenter = new UserChatPresenter(authManager);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_chat, container, false);

        //Тестовый код
        userChatPresenter.getAuthManager().signInEmail("dshebut@rambler.ru", "123456",
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

        RecyclerView chat = root.findViewById(R.id.chat);
        chat.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, true));

        List<MessageModel> messages = MessageProviderTemp.getMessages();
        Collections.reverse(messages);

        MessageAdapter messageAdapter = new MessageAdapter(getContext(), messages, userChatPresenter.getAuthManager());
        chat.setAdapter(messageAdapter);
        return root;

    }

    @Override
    public void showProgressMessage(String event) {

    }

    private void initData(){

    }
}
