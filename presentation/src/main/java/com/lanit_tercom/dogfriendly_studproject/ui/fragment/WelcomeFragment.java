package com.lanit_tercom.dogfriendly_studproject.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.WelcomeView;
import com.lanit_tercom.dogfriendly_studproject.ui.activity.WelcomeActivity;

public class WelcomeFragment extends BaseFragment implements WelcomeView {
    @Override
    public void initializePresenter() {} //Кажется, тут нет необходимости создавать презентер-класс

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_welcome, container, false);
        initInteractions(root);
        return root;
    }

    private void initInteractions(View root){
        WelcomeActivity base = (WelcomeActivity) getActivity();
        Button goToSignIn = root.findViewById(R.id.button_go_to_sign_in);
        Button goToSignUp = root.findViewById(R.id.button_go_to_sign_up);

        goToSignIn.setOnClickListener(v -> base.navigateToUserSignIn() );
        goToSignUp.setOnClickListener(v -> base.navigateToUserSignUp());
    }
}
