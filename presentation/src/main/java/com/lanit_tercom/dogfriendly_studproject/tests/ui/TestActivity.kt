package com.lanit_tercom.dogfriendly_studproject.tests.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager
import com.lanit_tercom.dogfriendly_studproject.data.entity.ChannelEntity
import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStore
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.FirebaseChannelEntityStore
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.mapper.ChannelEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.ChannelRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.domain.dto.ChannelDto
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.interactor.channel.AddChannelUseCase
import com.lanit_tercom.domain.interactor.channel.GetChannelsUseCase
import com.lanit_tercom.domain.interactor.channel.impl.AddChannelUseCaseImpl
import com.lanit_tercom.domain.interactor.channel.impl.GetChannelsUseCaseImpl
import com.lanit_tercom.domain.interactor.user.CreateUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.EditUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.GetUsersDetailsUseCase
import com.lanit_tercom.domain.interactor.user.impl.CreateUserDetailsUseCaseImpl
import com.lanit_tercom.domain.interactor.user.impl.EditUserDetailsUseCaseImpl
import com.lanit_tercom.domain.interactor.user.impl.GetUserDetailsUseCaseImpl
import com.lanit_tercom.domain.interactor.user.impl.GetUsersDetailsUseCaseImpl
import com.lanit_tercom.domain.repository.UserRepository
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*


class TestActivity : AppCompatActivity(), View.OnClickListener {
    private val threadExecutor: ThreadExecutor = JobExecutor.getInstance()
    private val postExecutionThread: PostExecutionThread = UIThread.getInstance()

    private val networkManager: NetworkManager = NetworkManagerImpl(this)
    private val userEntityStoreFactory = UserEntityStoreFactory(networkManager, null);
    private val userEntityDtoMapper = UserEntityDtoMapper()
    private val userRepository = UserRepositoryImpl(userEntityStoreFactory, userEntityDtoMapper)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        button_get.setOnClickListener(this)
        button_add.setOnClickListener(this)
        button_delete.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

        when (p0?.id) {
           //get
            R.id.button_get ->{

                val userGetChannelsUseCase = GetUsersDetailsUseCaseImpl(userRepository, threadExecutor, postExecutionThread);

                val getUsersCallback : GetUsersDetailsUseCase.Callback = object : GetUsersDetailsUseCase.Callback{

                    override fun onUsersDataLoaded(users: MutableList<UserDto>?) {
                        Log.i("TEST", users?.size.toString())
                    }

                    override fun onError(errorBundle: ErrorBundle?) {
                        Log.i("TEST", "ERROR")
                    }

                }

                userGetChannelsUseCase.execute(getUsersCallback);


            }
            //create
            R.id.button_add ->{
                val userDto= UserDto("0", "Олег");

                val createUserDetailsUseCase = CreateUserDetailsUseCaseImpl(userRepository, threadExecutor, postExecutionThread);

                val createUserCallback: CreateUserDetailsUseCase.Callback = object : CreateUserDetailsUseCase.Callback{
                    override fun onUserDataCreated() {
                        Log.i("TEST", "CREATED")
                    }

                    override fun onError(errorBundle: ErrorBundle?) {
                        Log.i("TEST", "ERROR")
                    }

                }

                createUserDetailsUseCase.execute(userDto, createUserCallback)
            }
            //update
            R.id.button_delete ->{
                val userDto= UserDto("-MD0BnHIw-Wmm4d6WNQG", "С");

                val editUserDetailsUseCase = EditUserDetailsUseCaseImpl(userRepository, threadExecutor, postExecutionThread);

                val editUserCallback: EditUserDetailsUseCase.Callback = object : EditUserDetailsUseCase.Callback{
                    override fun onUserDataEdited() {
                        Log.i("TEST", "EDITED")
                    }

                    override fun onError(errorBundle: ErrorBundle?) {
                        Log.i("TEST", "ERROR")
                    }

                }

                editUserDetailsUseCase.execute(userDto, editUserCallback)
            }
        }
    }

}