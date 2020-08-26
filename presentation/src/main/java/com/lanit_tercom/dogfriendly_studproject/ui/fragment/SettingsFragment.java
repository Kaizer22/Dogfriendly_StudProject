package com.lanit_tercom.dogfriendly_studproject.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.SettingsView;

public class SettingsFragment extends BaseFragment implements SettingsView {

    @Override
    public void initializePresenter() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_temp_settings, container, false);
        initInteractions(root);
        return root;
    }

    private void initInteractions(View root) {
        Button signOut = root.findViewById(R.id.button_sign_out);
        signOut.setOnClickListener(l -> signOut());
    }

    @Override
    public void signOut() {
        getActivity().finish();
    }
}
