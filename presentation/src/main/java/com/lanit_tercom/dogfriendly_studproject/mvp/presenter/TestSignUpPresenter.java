package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.TestSignUpView;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.TestSignUpFragment;

public class TestSignUpPresenter extends BasePresenter {
    private AuthManager authManager;
    private String currentUserId;
    private TestSignUpView view;

    public TestSignUpPresenter(AuthManager authManager){
        this.authManager = authManager;
    }

    public void setView(TestSignUpView view){
        this.view = view;
    }

    public void registerUser(String email, String password){
        authManager.createUserWithEmailPassword(email, password,
                new AuthManager.CreateUserCallback() {
                    @Override
                    public void onCreateUserFinished(String currentUserID) {
                        currentUserId = currentUserID;
                        view.changeSignUpStage(TestSignUpFragment.SignUpStage.GEOLOCATION_HINT);
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        view.showError(e.getMessage());
                    }
                });
    }

    @Override
    public void onDestroy() {

    }
}
