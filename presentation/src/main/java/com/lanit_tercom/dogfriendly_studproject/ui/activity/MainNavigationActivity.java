package com.lanit_tercom.dogfriendly_studproject.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel;
import com.lanit_tercom.dogfriendly_studproject.tests.ui.map.MapSettingsActivity;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.ChannelListFragment;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.MapFragment;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.PetCharacterFragment;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.PetDetailEditFragment;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.PetPhotoFragment;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.TestEmptyFragment;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserDetailEditFragment;
import com.lanit_tercom.dogfriendly_studproject.ui.fragment.UserDetailFragment;

import org.jetbrains.annotations.Nullable;

//Часть кода перенесена сюда из MapActivity и UserDetailActivity

public class MainNavigationActivity extends BaseActivity {
    private String userId;

    private int DEFAULT_CHECKED_ITEM = R.id.navigation_settings;

    //region supported fragments
    private MapFragment mapFragment;
    private UserDetailFragment userDetailFragment ;
    private ChannelListFragment channelListFragment;
    private TestEmptyFragment testEmptyFragment;

    private UserDetailEditFragment userDetailEditFragment;
    private PetDetailEditFragment petDetailEditFragment;
    private PetCharacterFragment petCharacterFragment;
    private PetPhotoFragment petPhotoFragment;
    //endregion

    public static Intent getCallingIntent(Context context){
        return new Intent(context, MainNavigationActivity.class);
    }

    @Override
    protected void initializeActivity(@Nullable Bundle savedInstanceState) {

    }

    private void initFragments(){
        mapFragment = new MapFragment();
        userDetailFragment = new UserDetailFragment(userId);
        channelListFragment = new ChannelListFragment();
        testEmptyFragment = new TestEmptyFragment();

        userDetailEditFragment = new UserDetailEditFragment(userId);
        petCharacterFragment = new PetCharacterFragment(userId);
        petPhotoFragment = new PetPhotoFragment(userId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_navigation_activity);
        AuthManager authManager = new AuthManagerFirebaseImpl();
        userId = authManager.getCurrentUserId();
        initFragments();

        initBottomNavigation();

        initInteractions();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupWithNavController(navView, navController);

    }

    private void initBottomNavigation() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarLayout mapTopBar = findViewById(R.id.map_app_bar);
        navView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        navView.setItemIconTintList(ContextCompat
                .getColorStateList(this, R.color.bottom_navigation_colors_list));
        navView.getMenu().findItem(DEFAULT_CHECKED_ITEM).setChecked(true);

        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_profile:
                    mapTopBar.setVisibility(View.GONE);
                    navView.getMenu().findItem(R.id.navigation_profile).setChecked(true);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment, userDetailFragment)
                            .commit();
                    break;
                case R.id.navigation_map:
                    mapTopBar.setVisibility(View.VISIBLE);
                    navView.getMenu().findItem(R.id.navigation_map).setChecked(true);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment, mapFragment)
                            .commit();
                    break;
                case R.id.navigation_channels:
                    mapTopBar.setVisibility(View.GONE);
                    navView.getMenu().findItem(R.id.navigation_channels).setChecked(true);;
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment, channelListFragment)
                            .commit();
                    break;
                case R.id.navigation_settings:
                    mapTopBar.setVisibility(View.GONE);
                    navView.getMenu().findItem(R.id.navigation_settings).setChecked(true);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initInteractions(){
        ImageButton mapSettings = findViewById(R.id.button_map_settings);
        mapSettings.setOnClickListener(l ->
                startActivity(new Intent(this, MapSettingsActivity.class)));
    }


    public void navigateToChat(String channelID){
        getNavigator().navigateToChat(this, channelID);
    }

    public void navigateToUserDetail(String userId){
        getNavigator().navigateToUserDetail(this, userId);
    }

    public void navigateToUserDetailObserver(String hostId, String checkedUserId){
        getNavigator().navigateToUserDetailObserver(this, hostId, checkedUserId);
    }

    public void navigateToWalkCreation(String userId){getNavigator().navigateToInvitationScreen(this, userId);}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    //region User and Pets profile
    public void startUserDetail(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new UserDetailFragment(userId))
                .commit();
    }


    public void startUserDetailEdit(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, userDetailEditFragment)
                .commit();
    }

    public void startPetDetailEdit(PetModel pet){
        petDetailEditFragment = new PetDetailEditFragment(userId); //вынужденная мера, тут иначе никак - нужен чистый фрагмент
        petDetailEditFragment.initializePet(pet);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, petDetailEditFragment)
                .commit();
    }
    public void startPetCharacterEdit(PetModel pet){
        petCharacterFragment.initializePet(pet);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, petCharacterFragment)
                .commit();
    }

    public void startPetPhotoEdit(PetModel pet){
        petPhotoFragment.initializePet(pet);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, petPhotoFragment)
                .commit();
    }

    //endregion

}
