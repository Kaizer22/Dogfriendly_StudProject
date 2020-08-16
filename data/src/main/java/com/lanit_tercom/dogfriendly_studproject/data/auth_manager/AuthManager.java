package com.lanit_tercom.dogfriendly_studproject.data.auth_manager;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Интерфейс для взаимодействия с разными реализациями аутентификации
 * @author dshebut@rambler.ru
 */
public interface AuthManager {

    interface SignInCallback{
        void onSignInFinished(String currentUserID);
        void onError(Exception e);
    }

    interface CreateUserCallback{
        void onCreateUserFinished(String currentUserID);
        void onError(Exception e);
    }

    interface SignOutCallback{
        void onSignOutFinished();
    }

    interface ResetPasswordCallback{
        void onPasswordReset();
        void onError(Exception e);
    }

    void createUserWithEmailPassword(String email, String password,
                                     CreateUserCallback createUserCallback);

    void signInEmail(String email, String password,
                     SignInCallback signInCallback);

    void resetPasswordWithEmail(String email,
                                ResetPasswordCallback resetPasswordCallback);

    void signInGoogle(GoogleSignInAccount account,
                      SignInCallback signInCallback);

    void signOut(SignOutCallback signOutCallback);

    boolean isSignedIn();

    String getCurrentUserId();
}
