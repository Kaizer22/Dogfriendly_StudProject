package com.lanit_tercom.dogfriendly_studproject.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.UserCache;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory;

import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl;
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.TestSignUpPresenter;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.TestSignUpView;
import com.lanit_tercom.dogfriendly_studproject.ui.activity.TestSignUpActivity;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.user.CreateUserDetailsUseCase;
import com.lanit_tercom.domain.interactor.user.impl.CreateUserDetailsUseCaseImpl;
import com.lanit_tercom.domain.repository.MessageRepository;
import com.lanit_tercom.domain.repository.UserRepository;
import com.lanit_tercom.library.data.manager.NetworkManager;
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl;

import org.jetbrains.annotations.NotNull;


public class TestSignUpFragment extends BaseFragment implements TestSignUpView {

    private TestSignUpPresenter presenter;
    private static SignUpStage currentStage = SignUpStage.REGISTRATION;

    private static boolean isNotificationsTurnedOn = false;

    public enum SignUpStage{
        REGISTRATION,
        GEOLOCATION_HINT,
        CHATS_AND_INVITATIONS_HINT,
        REGISTRATION_FINISHED
    }

    @Override
    public void initializePresenter() {
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        NetworkManager networkManager = new NetworkManagerImpl(getContext());
        UserEntityDtoMapper userEntityDtoMapper = new UserEntityDtoMapper();
        UserCache userCache = null;
        UserEntityStoreFactory userEntityStoreFactory =
                new UserEntityStoreFactory(networkManager, userCache);
        UserRepository userRepository = UserRepositoryImpl
                .getInstance(userEntityStoreFactory, userEntityDtoMapper);
        CreateUserDetailsUseCase createUser = new CreateUserDetailsUseCaseImpl(userRepository,
                threadExecutor, postExecutionThread);
        presenter = new TestSignUpPresenter(new AuthManagerFirebaseImpl(), createUser);
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
                root.findViewById(R.id.fragment_sign_up)
                        .setOnClickListener(l -> hideKeyboard(root));
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
                    baseActivity.navigateToUserProfile();
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
                password.getText().toString(),
                name.getText().toString());
    }

    private void hideKeyboard(View root) {
        InputMethodManager inputMethodManager  =
                (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(root.getWindowToken(),0);
    }

    private void turnOnGeolocation(){
        //TODO предложение пользователю включить геолокацию
        if (isGeoDisabled()){
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
        changeSignUpStage(SignUpStage.CHATS_AND_INVITATIONS_HINT);
    }

    public boolean isGeoDisabled() {
        LocationManager mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean mIsGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean mIsNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean mIsGeoDisabled = !mIsGPSEnabled && !mIsNetworkEnabled;
        return mIsGeoDisabled;
    }

    private void turnOnNotifications(){
        //TODO сохранять куда-то этот выбор пользователя
        isNotificationsTurnedOn = true;
        changeSignUpStage(SignUpStage.REGISTRATION_FINISHED);
        //NotificationCompat.Builder builder =
                //new NotificationCompat.Builder(getContext())
                        //.setSmallIcon(R.mipmap.ic_launcher)
                       // .setContentTitle("Title")
                        //.setContentText("Notification text");

        //Notification notification = builder.build();

        //NotificationManager notificationManager =
                //(NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        //notificationManager.notify(1, notification);

    }

}
