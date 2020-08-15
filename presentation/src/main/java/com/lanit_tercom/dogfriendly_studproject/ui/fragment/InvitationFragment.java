package com.lanit_tercom.dogfriendly_studproject.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.data.entity.WalkEntity;
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.WalkCache;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.walk.WalkEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.WalkEntityDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.data.repository.WalkRepositoryImpl;
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread;
import com.lanit_tercom.dogfriendly_studproject.mapper.WalkModelDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.WalkModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.InvitationPresenter;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.InvitationView;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.walk.AddWalkUseCase;
import com.lanit_tercom.domain.interactor.walk.impl.AddWalkUseCaseImpl;
import com.lanit_tercom.domain.repository.WalkRepository;
import com.lanit_tercom.library.data.manager.NetworkManager;
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class InvitationFragment extends BaseFragment implements InvitationView {

    private InvitationPresenter invitationPresenter;
    private WalkModel walkModel;

    private Slider radiusSlider;
    private MaterialButton createButton;
    private EditText invitationNameView;
    private EditText invitationDescriptionView;
    private RadioGroup accessToChatRbt;
    private RadioGroup timeOfVisibilityRbt;


    private String invitationName;
    private String invitationDescription;
    private boolean freeAccess = true;
    private int radius;
    private int timeOfVisibility = 6;


    public InvitationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invitation, container, false);
        //invitationPresenter.setView(this);

        initElements(view);
        initSlider(view);

        return view;
    }

    @Override
    public void initializePresenter() {
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        AuthManager authManager = new AuthManagerFirebaseImpl();
        NetworkManager networkManager = new NetworkManagerImpl(getContext());
        WalkEntityDtoMapper walkEntityDtoMapper = new WalkEntityDtoMapper();
        WalkCache walkCache = new WalkCache() {
            @Override
            public void saveWalk(String userId, WalkEntity walkEntity) {

            }
        };

        WalkEntityStoreFactory walkEntityStoreFactory = new WalkEntityStoreFactory(networkManager, walkCache);
        WalkRepositoryImpl walkRepository = WalkRepositoryImpl.getInstance(walkEntityStoreFactory, walkEntityDtoMapper);
        AddWalkUseCase addWalkUseCase = new AddWalkUseCaseImpl(walkRepository, threadExecutor, postExecutionThread);

        invitationPresenter = new InvitationPresenter(authManager, addWalkUseCase);
    }


    private void initElements(View view){
        radiusSlider = view.findViewById(R.id.slider_radius);
        createButton = view.findViewById(R.id.invitation_create_btn);
        invitationNameView = view.findViewById(R.id.invitation_name);
        invitationDescriptionView = view.findViewById(R.id.invitation_description);
        accessToChatRbt = view.findViewById(R.id.rg_access_to_chat);
        timeOfVisibilityRbt = view.findViewById(R.id.rg_visible_radius);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createInvitation(view);
            }
        });

        accessToChatRbt.setOnCheckedChangeListener((group, checkedId) -> {
            switch (group.getCheckedRadioButtonId()){
                case R.id.access_to_chat_free:
                    freeAccess = true;
                    break;
                case R.id.access_to_chat_request:
                    freeAccess = false;
                    break;
            }
        });

        timeOfVisibilityRbt.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.radius_six_hours:
                    timeOfVisibility = 6;
                    break;
                case R.id.radius_twelve_hours:
                    timeOfVisibility = 12;
                    break;
                case R.id.radius_day:
                    timeOfVisibility = 24;
                    break;
            }
        });
    }

    private void initSlider(View view){
        radiusSlider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                String result;
                result = (int) radiusSlider.getValue() + " км";
                return result;
            }
        });
        radiusSlider.setLabelBehavior(LabelFormatter.LABEL_WITHIN_BOUNDS);
    }

    public void createInvitation(View view){
        invitationName = invitationNameView.getText().toString();
        invitationDescription = invitationDescriptionView.getText().toString();
        radius = (int) radiusSlider.getValue();

        walkModel = new WalkModel();
        walkModel.setWalkName(invitationName);
        walkModel.setDescription(invitationDescription);
        walkModel.setFreeAccess(freeAccess);
        walkModel.setRadiusOfVisibility(radius);
        walkModel.setTimeOfVisibility(timeOfVisibility);

        invitationPresenter.addWalk(walkModel);

        Toast.makeText(getContext(), "Приглашение было создано", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(@NotNull String message) {

    }
}