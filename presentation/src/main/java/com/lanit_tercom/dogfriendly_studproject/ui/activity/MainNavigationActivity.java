package com.lanit_tercom.dogfriendly_studproject.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.ChannelListFragment;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.MapFragment;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.TestEmptyFragment;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserDetailFragment;

import org.jetbrains.annotations.Nullable;

public class MainNavigationActivity extends BaseActivity {

    //TODO подсвечивать выбранный элемент навигационной панели

    public static Intent getCallingIntent(Context context){
        return new Intent(context, MainNavigationActivity.class);
    }

    @Override
    protected void initializeActivity(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_navigation_activity);
        AuthManager authManager = new AuthManagerFirebaseImpl();

        MapFragment mapFragment = new MapFragment();
        UserDetailFragment userDetailFragment = new UserDetailFragment(
                authManager.getCurrentUserId());
        ChannelListFragment channelListFragment = new ChannelListFragment();
        TestEmptyFragment testEmptyFragment = new TestEmptyFragment();



        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED);
        navView.setItemIconTintList(ContextCompat
                .getColorStateList(this, R.color.bottom_navigation_colors_list));

        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_profile:
                   getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment, userDetailFragment)
                           .commit();
                    break;
               case R.id.navigation_map:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment, mapFragment)
                            .commit();
                    break;
               case R.id.navigation_channels:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment, channelListFragment)
                            .commit();
                    break;
                case R.id.navigation_settings:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment, testEmptyFragment)
                            .commit();
                    break;
            }
            return false;
        });

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_profile,
                R.id.navigation_map,
                R.id.navigation_settings,
                R.id.navigation_channels)
                .build();

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupWithNavController(navView, navController);

    }

    public void navigateToChat(String channelID){
        getNavigator().navigateToChat(this, channelID);
    }

    public void navigateToUserDetail(String userId){
        getNavigator().navigateToUserDetail(this, userId);
    }



}
