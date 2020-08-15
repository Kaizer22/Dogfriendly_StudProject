package com.lanit_tercom.dogfriendly_studproject.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor;
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.library.data.manager.NetworkManager;
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl;


public class InvitationFragment extends BaseFragment {

    Slider slider;
    Button button;


    public InvitationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invitation, container, false);

        initSlider(view);

        return view;
    }

    @Override
    public void initializePresenter() {
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        AuthManager authManager = new AuthManagerFirebaseImpl();
        NetworkManager networkManager = new NetworkManagerImpl(getContext());

    }

    private void initSlider(View view){
        slider = view.findViewById(R.id.slider_radius);

        slider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                String result;
                result = (int) slider.getValue() + " км";
                return result;
            }
        });
        slider.setLabelBehavior(LabelFormatter.LABEL_WITHIN_BOUNDS);

        button = view.findViewById(R.id.invention_create_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Button was clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }




}