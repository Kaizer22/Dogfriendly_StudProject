package com.lanit_tercom.data.auth_manager;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface AuthManager {

    void createUserWithEmailPassword(String email, String password);

    void signInEmail(String email, String password);

    void restartPasswordWithEmail();

    void signInGoogle(GoogleSignInAccount account);

    void signOut();


    boolean isSignedIn();

    String getCurrentUserId();
}
