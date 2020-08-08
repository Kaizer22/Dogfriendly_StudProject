package com.lanit_tercom.dogfriendly_studproject.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.ResetPasswordPresenter;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.ResetPasswordView;
import com.lanit_tercom.dogfriendly_studproject.ui.activity.ResetPasswordActivity;

import org.jetbrains.annotations.NotNull;

public class ResetPasswordFragment extends BaseFragment implements ResetPasswordView {

    private ResetPasswordPresenter presenter;
    private TextView resetError;
    private static boolean isFinished = false;

    @Override
    public void initializePresenter() {
        AuthManager authManager = new AuthManagerFirebaseImpl();
        presenter = new ResetPasswordPresenter(authManager);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root;
        if (isFinished){
            root = inflater.inflate(R.layout.fragment_reset_password_finished, container, false);
        }else{
            root = inflater.inflate(R.layout.fragment_reset_password, container, false);
        }
        initInteractions(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
    }

    private void initInteractions(View root){
        ResetPasswordActivity baseActivity = (ResetPasswordActivity) getActivity();
        if (isFinished){
            Button toSignIn = root.findViewById(R.id.button_sign_in);
            toSignIn.setOnClickListener(l -> {
                isFinished = false;
                baseActivity.navigateToSignIn();
            });
        }else{
            EditText emailField = root.findViewById(R.id.enter_email);
            ImageButton backToSignIn = root.findViewById(R.id.button_back_to_sign_in);
            backToSignIn.setOnClickListener(l -> baseActivity.navigateToSignIn());

            Button resetPassword = root.findViewById(R.id.button_reset_password);
            resetPassword.setOnClickListener(l ->
                    resetPassword(emailField.getText().toString()));

            resetError = root.findViewById(R.id.reset_password_error);
        }
    }

    private void resetPassword(String email){
        if(email.equals("")){
            resetError.setVisibility(View.VISIBLE);
            resetError.setText(R.string.empty_email);
        }else {
            presenter.resetPassword(email);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void changeCondition(boolean isFinished) {
        ResetPasswordFragment.isFinished = isFinished;
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .detach(this)
                .attach(this)
                .commit();
    }

    @Override
    public void showResetError(int resourceID) {
        resetError.setVisibility(View.VISIBLE);
        resetError.setText(resourceID);
    }

    @Override
    public void showError(@NotNull String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG)
                .show();
    }
}
