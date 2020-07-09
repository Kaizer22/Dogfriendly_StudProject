package com.lanit_tercom.data.auth_manager.firebase_impl;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.lanit_tercom.data.auth_manager.AuthManager;


public class AuthManagerFirebaseImpl implements AuthManager {
    private FirebaseAuth firebaseAuth;

    //private GoogleApiClient googleApiClient;

    public AuthManagerFirebaseImpl(){
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    //region Implementation
    @Override
    public void createUserWithEmailPassword(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Log.d("AUTH_MANAGER", "User has been created");
                        sendVerificationEmail();
                    }else{
                        Log.w("AUTH_MANAGER", task.getException().getMessage());
                    }
                });
    }

    @Override
    public void signInEmail(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Log.d("AUTH_MANAGER", "Successfully signed in");
                    }else{
                        Log.w("AUTH_MANAGER", task.getException().getMessage());
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
    public void signOut() {
        firebaseAuth.signOut();
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
            } else{
            Log.w("AUTH_MANAGER", "Can not send a verification email. " +
                    "Current user is null");
        }

        }
    }
   /* private void deleteCache() {
        Realm.getDefaultInstance().executeTransactionAsync(realm -> realm.deleteAll());
    }
   */
}
