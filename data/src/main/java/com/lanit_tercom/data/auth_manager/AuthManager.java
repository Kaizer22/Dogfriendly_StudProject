package com.lanit_tercom.data.auth_manager;

public interface AuthManager {

    void createUserWithEmailPassword(String email, String password);

    void signInEmail(String email, String password);

    void restartPasswordWithEmail();

    void signOut();


    boolean isSignedIn();

    String getCurrentUserId();
}
