package com.lanit_tercom.dogfriendly_studproject.ui.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.data.entity.PetEntity;
import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
import com.lanit_tercom.dogfriendly_studproject.data.entity.WalkEntity;
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.UserCache;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.WalkCache;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.walk.FirebaseWalkEntityStore;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.walk.WalkEntityStoreFactory;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.data.mapper.WalkEntityDtoMapper;
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl;
import com.lanit_tercom.dogfriendly_studproject.data.repository.WalkRepositoryImpl;
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.WalkModel;
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.WalkPresenter;
import com.lanit_tercom.dogfriendly_studproject.mvp.view.WalkDetailsView;
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.WalkMemberListAdapter;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase;
import com.lanit_tercom.domain.interactor.user.GetUsersByIdUseCase;
import com.lanit_tercom.domain.interactor.user.GetUsersDetailsUseCase;
import com.lanit_tercom.domain.interactor.user.impl.GetUserDetailsUseCaseImpl;
import com.lanit_tercom.domain.interactor.user.impl.GetUsersByIdUseCaseImpl;
import com.lanit_tercom.domain.interactor.user.impl.GetUsersDetailsUseCaseImpl;
import com.lanit_tercom.domain.interactor.walk.AddWalkUseCase;
import com.lanit_tercom.domain.interactor.walk.DeleteWalkUseCase;
import com.lanit_tercom.domain.interactor.walk.EditWalkUseCase;
import com.lanit_tercom.domain.interactor.walk.GetWalkUseCase;
import com.lanit_tercom.domain.interactor.walk.impl.DeleteWalkUseCaseImpl;
import com.lanit_tercom.domain.interactor.walk.impl.EditWalkUseCaseImpl;
import com.lanit_tercom.domain.interactor.walk.impl.GetWalkUseCaseImpl;
import com.lanit_tercom.library.data.manager.NetworkManager;
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WalkFragment extends BaseFragment implements WalkDetailsView {

    private RecyclerView walkMemberListRecyclerView;
    private WalkMemberListAdapter walkMemberListAdapter;
    private WalkPresenter walkPresenter;

    private TextView walkNameView;
    private TextView membersQuantityView;
    private ImageView walkAccessView;
    private TextView walkAccessDescriptionView;
    private TextView walkDescriptionView;
    private CircleImageView creatorAvatarView;
    private TextView creatorNameView;
    private TextView creatorAgeView;

    private String walkName;
    private String membersQuantity;
    private boolean walkAccess;
    private String walkAccessDescription;
    private String walkDescription;
    private Uri creatorAvatar; //TODO change to Uri
    private String creatorName;
    private String creatorAge;
    private List<String> membersId;


    public WalkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_walk, container, false);

        walkPresenter.setView(this);
        initRecyclerView(view);
        initElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.walkPresenter.setView(this);
        if (savedInstanceState == null){
            this.loadWalkDetails();
        }
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

        UserEntityDtoMapper userEntityDtoMapper = new UserEntityDtoMapper();
        /*UserCache userCache = new UserCache() {
            @Override
            public void saveUser(String userId, UserEntity userEntity) {

            }
        };*/

        WalkEntityStoreFactory walkEntityStoreFactory = new WalkEntityStoreFactory(networkManager, walkCache);
        WalkRepositoryImpl walkRepository = WalkRepositoryImpl.getInstance(walkEntityStoreFactory, walkEntityDtoMapper);

        UserEntityStoreFactory userEntityStoreFactory = new UserEntityStoreFactory(networkManager);
        UserRepositoryImpl userRepository = UserRepositoryImpl.getInstance(userEntityStoreFactory, userEntityDtoMapper);

        GetWalkUseCase getWalkUseCase = new GetWalkUseCaseImpl(walkRepository, threadExecutor, postExecutionThread);
        EditWalkUseCase editWalkUseCase = new EditWalkUseCaseImpl(walkRepository, threadExecutor, postExecutionThread);
        DeleteWalkUseCase deleteWalkUseCase = new DeleteWalkUseCaseImpl(walkRepository, threadExecutor, postExecutionThread);
        GetUserDetailsUseCase getUserDetailsUseCase = new GetUserDetailsUseCaseImpl(userRepository, threadExecutor, postExecutionThread);
        GetUsersByIdUseCase getMembersUseCase = new GetUsersByIdUseCaseImpl(userRepository, threadExecutor, postExecutionThread);

        this.walkPresenter = new WalkPresenter(authManager,
                getWalkUseCase,
                editWalkUseCase,
                deleteWalkUseCase,
                getUserDetailsUseCase,
                getMembersUseCase);
    }

    private void initRecyclerView(@NotNull View view){
        walkMemberListRecyclerView = view.findViewById(R.id.rv_walk_members);
        walkMemberListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        walkMemberListAdapter = new WalkMemberListAdapter(getContext());
        walkMemberListRecyclerView.setAdapter(walkMemberListAdapter);
    }

    private void initElements(@NotNull View view){
        this.walkNameView = view.findViewById(R.id.walk_title);
        this.membersQuantityView = view.findViewById(R.id.members_quantity);
        this.walkAccessView = view.findViewById(R.id.walk_access);
        this.walkAccessDescriptionView = view.findViewById(R.id.walk_access_description);
        this.walkDescriptionView = view.findViewById(R.id.walk_description);
        this.creatorAvatarView = view.findViewById(R.id.walk_creator_image);
        this.creatorNameView = view.findViewById(R.id.walk_creator_name);
        this.creatorAgeView = view.findViewById(R.id.walk_creator_age);
    }

    @Override
    public void renderCurrentWalk(WalkModel walkModel) {
        this.walkPresenter.getCreatorDetails(walkModel.getCreator());
        this.walkPresenter.getWalkMembersDetails(walkModel.getMembers());

        this.walkName = walkModel.getWalkName();
        this.membersQuantity = walkModel.getMembers().size() + " участник(ов)";
        this.walkAccess = walkModel.isFreeAccess();
        this.walkDescription = walkModel.getDescription();

        walkNameView.setText(walkName);
        membersQuantityView.setText(membersQuantity);
        if (walkAccess){
            walkAccessView.setVisibility(View.INVISIBLE);
            walkAccessDescriptionView.setText(R.string.access_free_description);
        }
        else {
            walkAccessView.setVisibility(View.VISIBLE);
            walkAccessDescriptionView.setText(R.string.access_request_description);
        }
        walkDescriptionView.setText(walkDescription);

        //TODO change
        //walkMemberListAdapter.setWalkMembers();
    }

    @Override
    public void renderWalkCreator(UserModel userModel) {
        this.creatorName = userModel.getName();
        this.creatorAge = userModel.getAge().toString();
        this.creatorAvatar = userModel.getAvatar();
        creatorAvatarView.setImageResource(R.drawable.ic_user_profile_image); //TODO change
        creatorNameView.setText(creatorName);
        creatorAgeView.setText(creatorAge);
    }

    @Override
    public void renderWalkMembers(List<UserModel> members) {
        walkMemberListAdapter.setWalkMembers(members);
    }


    private void loadWalkDetails(){
        this.walkPresenter.initialize("-MEnwIuW7ATZ1CsiganB");
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

    private void testWalkFirebase(){
        FirebaseWalkEntityStore firebaseWalkEntityStore = new FirebaseWalkEntityStore();
    }
}