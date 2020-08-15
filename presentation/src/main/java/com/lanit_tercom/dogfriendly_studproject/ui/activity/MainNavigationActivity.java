package com.lanit_tercom.dogfriendly_studproject.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.lanit_tercom.dogfriendly_studproject.R;

import org.jetbrains.annotations.Nullable;

public class MainNavigationActivity extends BaseActivity {

    //TODO получение ID текущекго пользователя для передачи в дочерние
    // фрагменты данной навигационной activity

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

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED);
        navView.setItemIconTintList(ContextCompat
                .getColorStateList(this, R.color.bottom_navigation_colors_list));

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_profile,
                R.id.navigation_map,
                R.id.navigation_settings,
                R.id.navigation_channels)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void navigateToChat(String channelID){
        getNavigator().navigateToChat(this, channelID);
    }
}
