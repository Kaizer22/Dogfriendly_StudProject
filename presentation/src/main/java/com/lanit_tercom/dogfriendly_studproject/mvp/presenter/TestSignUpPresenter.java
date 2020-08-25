package com.lanit_tercom.dogfriendly_studproject.mvp.presenter;

import android.net.Uri;

import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.TestSignUpView;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.TestSignUpFragment;
import com.lanit_tercom.domain.dto.PetDto;
import com.lanit_tercom.domain.dto.UserDto;
import com.lanit_tercom.domain.exception.ErrorBundle;
import com.lanit_tercom.domain.interactor.user.CreateUserDetailsUseCase;
import com.lanit_tercom.domain.interactor.user.impl.CreateUserDetailsUseCaseImpl;
import com.lanit_tercom.domain.repository.UserRepository;
import com.lanit_tercom.library.data.manager.NetworkManager;
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl;

import java.util.HashMap;
import java.util.LinkedList;

public class TestSignUpPresenter extends BasePresenter {
    private AuthManager authManager;
    private String currentUserId;
    private TestSignUpView view;

    private String DEFAULT_USER_AVATAR= "https://firebasestorage.googleapis.com" +
            "/v0/b/dogfriendlystudproject.appspot.com/o" +
            "/Uploads%2FJurrTX2vrYWWX4Svo9CO42xFrnk2%2Favatar?alt=media&token=b5f13ce4-a1bd-4468-8bf8-925e3a5e759c";

    private CreateUserDetailsUseCase createUser;

    public TestSignUpPresenter(AuthManager authManager, CreateUserDetailsUseCase createUser){
        this.authManager = authManager;
        this.createUser = createUser;
    }

    public void setView(TestSignUpView view){
        this.view = view;
    }

    public void registerUser(String email, String password, String name){
        authManager.createUserWithEmailPassword(email, password,
                new AuthManager.CreateUserCallback() {
                    @Override
                    public void onCreateUserFinished(String currentUserID) {
                        currentUserId = currentUserID;
                        view.changeSignUpStage(TestSignUpFragment.SignUpStage.GEOLOCATION_HINT);
                        UserDto userDto = new UserDto(currentUserId, name,
                                0, "Расскажите о себе", "Ваши планы на ближаюшую прогулку",
                                new HashMap<String, PetDto>(), DEFAULT_USER_AVATAR);
                        createUser.execute(userDto, new CreateUserDetailsUseCase.Callback() {
                            @Override
                            public void onUserDataCreated() {
                                view.changeSignUpStage(TestSignUpFragment.SignUpStage.GEOLOCATION_HINT);
                            }

                            @Override
                            public void onError(ErrorBundle errorBundle) {
                                errorBundle.getException().printStackTrace();
                            }
                        });
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
