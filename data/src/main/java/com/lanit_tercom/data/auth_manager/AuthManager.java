package com.lanit_tercom.data.auth_manager;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Интерфейс для взаимодействия с разными реализациями аутентификации
 * @author dshebut@rambler.ru
 */
public interface AuthManager {

    interface SignInCallback{
        void OnSignInFinished(String currentUserID);
        void OnError(Exception e);
    }

    interface CreateUserCallback{
        void OnCreateUserFinished(String currentUserID);
        void OnError(Exception e);
    }

    interface SignOutCallback{
        void OnSignOutFinished();
    }

    void createUserWithEmailPassword(String email, String password,
                                     CreateUserCallback createUserCallback);

    void signInEmail(String email, String password,
                     SignInCallback signInCallback);

    void restartPasswordWithEmail();

    void signInGoogle(GoogleSignInAccount account,
                      SignInCallback signInCallback);

    void signOut(SignOutCallback signOutCallback);

    boolean isSignedIn();

    String getCurrentUserId();
}
