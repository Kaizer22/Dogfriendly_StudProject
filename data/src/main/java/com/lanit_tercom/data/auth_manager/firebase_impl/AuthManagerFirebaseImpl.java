package com.lanit_tercom.data.auth_manager.firebase_impl;

import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.GoogleAuthProvider;
import com.lanit_tercom.data.auth_manager.AuthManager;

import io.realm.Realm;

/**
 * Реализация аутентификации с помощью Firebase
 * @author dshebut@rambler.ru
 */
public class AuthManagerFirebaseImpl implements AuthManager {

    private FirebaseAuth firebaseAuth;

    public AuthManagerFirebaseImpl(){
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    //region Implementation
    @Override
    public void createUserWithEmailPassword(String email, String password, CreateUserCallback createUserCallback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Log.d("AUTH_MANAGER", "User has been created");
                        sendVerificationEmail();
                        createUserCallback
                                .OnCreateUserFinished(firebaseAuth.getCurrentUser().getUid());
                    }else{
                        createUserCallback.OnError(task.getException());
                    }
                });
    }

    @Override
    public void signInEmail(String email, String password, SignInCallback signInCallback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Log.d("AUTH_MANAGER", "Successfully signed in");
                        signInCallback.OnSignInFinished(firebaseAuth.getCurrentUser().getUid());
                    }else{
                        signInCallback.OnError(task.getException());
                    }
                });

    }

    @Override
    public void restartPasswordWithEmail() {
        String email = firebaseAuth.getCurrentUser()
                .getEmail();

        if (email != null){
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Log.d("AUTH_MANAGER", "Password reset email has been sent to "
                                    + email);
                        }else{
                            Log.w("AUTH_MANAGER", task.getException().getMessage());
                        }
                    });
        }else{
            Log.w("AUTH_MANAGER", "Can not restart password with email. " +
                    "Current user is null");
        }
    }

    @Override
    public void signInGoogle(GoogleSignInAccount account, SignInCallback signInCallback) {
        AuthCredential credential = GoogleAuthProvider
                .getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Log.d("AUTH_MANAGER", "Successfully signed in with Google");
                signInCallback.OnSignInFinished(firebaseAuth.getCurrentUser().getUid());
            }else {
                signInCallback.OnError(task.getException());
            }
        });
    }

    @Override
    public void signOut(SignOutCallback signOutCallback) {
        firebaseAuth.signOut();
        signOutCallback.OnSignOutFinished();

        //deleteCache();
    }

    @Override
    public boolean isSignedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    @Override
    public String getCurrentUserId() {
        String id = null;
        if (isSignedIn()) {
            id = firebaseAuth.getCurrentUser().getUid();
        }
        return id;
    }
    //endregion

    private void sendVerificationEmail(){
        if (isSignedIn()){
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null){
                user.sendEmailVerification()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                Log.d("AUTH_MANAGER",
                                        "Verification email has been sent to "
                                                + user.getEmail() );
                            }else{
                                Log.w("AUTH_MANAGER", task.getException().getMessage());
                            }
                        });
            } else {
                Log.w("AUTH_MANAGER", "Can not send a verification email. " +
                        "Current user is null");
            }
        }
    }
    private void deleteCache() {
        Realm.getDefaultInstance().executeTransactionAsync(
                realm -> realm.deleteAll());
    }
}