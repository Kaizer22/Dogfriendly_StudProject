package com.lanit_tercom.dogfriendly_studproject.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.TestSignUpPresenter;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.TestSignUpView;
import com.lanit_tercom.dogfriendly_studproject.ui.activity.TestSignUpActivity;

import org.jetbrains.annotations.NotNull;

public class TestSignUpFragment extends BaseFragment implements TestSignUpView {

    private TestSignUpPresenter presenter;
    private static SignUpStage currentStage = SignUpStage.REGISTRATION;



    public enum SignUpStage{
        REGISTRATION,
        GEOLOCATION_HINT,
        CHATS_AND_INVITATIONS_HINT,
        REGISTRATION_FINISHED
    }

    @Override
    public void initializePresenter() {
        presenter = new TestSignUpPresenter(new AuthManagerFirebaseImpl());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root;
        switch(currentStage){
            case GEOLOCATION_HINT:
                root = inflater.inflate(R.layout.fragment_sign_up_2, container, false);
                break;
            case CHATS_AND_INVITATIONS_HINT:
                root = inflater.inflate(R.layout.fragment_sign_up_3, container, false);
                break;
            case REGISTRATION_FINISHED:
                root = inflater.inflate(R.layout.fragment_sign_up_4, container, false);
                break;
            default:
                root = inflater.inflate(R.layout.fragment_sign_up, container, false);
        }
        initInteractions(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
    }

    @Override
    public void changeSignUpStage(@NotNull SignUpStage stage) {
        currentStage = stage;
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .detach(this)
                .attach(this)
                .commit();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(@NotNull String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG)
        .show();
    }

    private void initInteractions(View root){
        TestSignUpActivity baseActivity = (TestSignUpActivity) getActivity();
        switch (currentStage) {
            case REGISTRATION:
                Button toGeolocationHint = root.findViewById(R.id.button_sign_up);
                TextView toSignInLink = root.findViewById(R.id.to_sign_in_link);

                toGeolocationHint.setOnClickListener(l -> register(root) );
                toSignInLink.setOnClickListener(l -> baseActivity.navigateToSignIn());
                break;
            case GEOLOCATION_HINT:
                Button turnOnGeolocation = root.findViewById(R.id.button_turn_on_geolocation);
                turnOnGeolocation.setOnClickListener(l -> turnOnGeolocation());
                break;
            case CHATS_AND_INVITATIONS_HINT:
                Button turnOnNotifications = root.findViewById(R.id.button_turn_on_notifications);
                Button skip = root.findViewById(R.id.button_skip_turning_on_notifications);

                turnOnNotifications.setOnClickListener(l -> turnOnNotifications());
                skip.setOnClickListener(l -> changeSignUpStage(SignUpStage.REGISTRATION_FINISHED));
                break;
            case REGISTRATION_FINISHED:
                Button finishRegistration = root.findViewById(R.id.button_finish_registration);
                finishRegistration.setOnClickListener(l ->{
                    currentStage = SignUpStage.REGISTRATION;
                    baseActivity.navigateToSignIn();
                });
                //finishRegistration.setOnClickListener(l -> baseActivity.navigateToUserProfile());
                break;
        }
    }

    private void register(View root){
        EditText email = root.findViewById(R.id.edit_email);
        EditText password = root.findViewById(R.id.edit_password);
        EditText name = root.findViewById(R.id.edit_name);

        presenter.registerUser(email.getText().toString(),
                password.getText().toString());
    }

    private void turnOnGeolocation(){
        //TODO предложение пользователю включить геолокацию
        changeSignUpStage(SignUpStage.CHATS_AND_INVITATIONS_HINT);
    }

    private void turnOnNotifications(){
        //TODO предложение пользователю включить уведомления от приложения
        changeSignUpStage(SignUpStage.REGISTRATION_FINISHED);
    }

}