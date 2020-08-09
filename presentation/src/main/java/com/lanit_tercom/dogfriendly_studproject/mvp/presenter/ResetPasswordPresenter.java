package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;


import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.ResetPasswordView;

public class ResetPasswordPresenter extends BasePresenter {
    private AuthManager authManager;

    private ResetPasswordView resetPasswordView;


    public ResetPasswordPresenter(AuthManager authManager){
        this.authManager = authManager;
    }

    public void setView(ResetPasswordView resetPasswordView){
        this.resetPasswordView = resetPasswordView;
    }

    public void resetPassword(String email){
        authManager.resetPasswordWithEmail(email,
                new AuthManager.ResetPasswordCallback() {
                    @Override
                    public void onPasswordReset() {
                        resetPasswordView.changeViewCondition(true);
                    }

                    @Override
                    public void onError(Exception e) {
                        //TODO найти более корректый способ распознавать exception
                        e.printStackTrace();
                        String exception = e.toString();
                        if (exception.contains("FirebaseAuthInvalidCredentialsException")){
                            resetPasswordView.showResetError(
                                    R.string.user_with_that_email_address_not_found);
                        }else if (exception.contains("FirebaseNetworkException")){
                            resetPasswordView.showResetError(
                                    R.string.no_internet_connection);
                        }else{
                            resetPasswordView.showResetError(
                                    R.string.unknown_error);
                        }
                    }
                });
    }
}
