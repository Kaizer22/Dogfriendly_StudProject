package com.lanit_tercom.dogfriendly_studproject.tests.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.domain.dto.PetDto
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.interactor.user.AddPetUseCase
import com.lanit_tercom.domain.interactor.user.CreateUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.DeleteUserDetailUseCase
import com.lanit_tercom.domain.interactor.user.GetUsersDetailsUseCase
import com.lanit_tercom.domain.interactor.user.impl.AddPetUseCaseImpl
import com.lanit_tercom.domain.interactor.user.impl.CreateUserDetailsUseCaseImpl
import com.lanit_tercom.domain.interactor.user.impl.DeleteUserDetailUseCaseImpl
import com.lanit_tercom.domain.interactor.user.impl.GetUsersDetailsUseCaseImpl
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl
import kotlinx.android.synthetic.main.activity_test.*

class UserTestActivity : AppCompatActivity(), View.OnClickListener {
    private val threadExecutor: ThreadExecutor = JobExecutor.getInstance()
    private val postExecutionThread: PostExecutionThread = UIThread.getInstance()

    private val networkManager: NetworkManager = NetworkManagerImpl(this)
    private val userEntityStoreFactory = UserEntityStoreFactory(networkManager, null);
    private val userEntityDtoMapper = UserEntityDtoMapper()
    private val userRepository = UserRepositoryImpl(userEntityStoreFactory, userEntityDtoMapper)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        button_delete.setOnClickListener(this)
        button_get.setOnClickListener(this)
        button_add.setOnClickListener(this)
        button_del.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

        when (p0?.id) {
            //GET USERS
            R.id.button_get ->{

                val getUsersDetailsUseCase = GetUsersDetailsUseCaseImpl(userRepository, threadExecutor, postExecutionThread)
                val getUsersCallback: GetUsersDetailsUseCase.Callback = object : GetUsersDetailsUseCase.Callback{
                    override fun onUsersDataLoaded(users: MutableList<UserDto>?) {
                        Log.i("TEST_ACTIVITY", "GOT USER DETAILS SUCCESSFULLY")
                    }

                    override fun onError(errorBundle: ErrorBundle?) {
                        Log.i("TEST_ACTIVITY", "ERROR WHILE USER CREATION")
                    }

                }

                getUsersDetailsUseCase.execute(getUsersCallback)
            }
            //ADD USER
            R.id.button_add ->{
                val user = UserDto()


                user.id = "1"
                user.name = "карасик"
                user.age = 21
                user.avatar = "qweqweqwe"
                user.about = "ewqewqewq"
                user.plans = "qweewqqwe"



                val createUserUseCase = CreateUserDetailsUseCaseImpl(userRepository, threadExecutor, postExecutionThread)

                val createUserCallback: CreateUserDetailsUseCase.Callback = object : CreateUserDetailsUseCase.Callback{
                    override fun onUserDataCreated() {
                        Log.i("TEST_ACTIVITY", "CREATED SUCCESFULLY")
                    }

                    override fun onError(errorBundle: ErrorBundle?) {
                        Log.i("TEST_ACTIVITY", "ERROR WHILE USER CREATION")
                    }


                }

                createUserUseCase.execute(user ,createUserCallback)

            }
            //SUDDENLY ADD PET
            R.id.button_delete ->{
                val pet =  PetDto()
                pet.about = "qweq"
                pet.age = 2
                pet.breed ="rewd"

                val addPetUseCase = AddPetUseCaseImpl(userRepository, threadExecutor, postExecutionThread)

                val addPetCallback: AddPetUseCase.Callback = object : AddPetUseCase.Callback{
                    override fun onError(errorBundle: ErrorBundle?) {
                        Log.i("TEST_ACTIVITY", "ADDED_SUCCESFULLY")
                    }

                    override fun onPetAdded() {
                        Log.i("TEST_ACTIVITY", "ERROR_WHILE_ADDING_NEW_PET")
                    }

                }

                addPetUseCase.execute("-MEYGzlqgcVxSHRV5LQ9", pet, addPetCallback)

            }
            //DELETE_USER
            R.id.button_del ->{
                val deleteUserDetailUseCase = DeleteUserDetailUseCaseImpl(userRepository, threadExecutor, postExecutionThread)

                val deleteUserCallback: DeleteUserDetailUseCase.Callback = object : DeleteUserDetailUseCase.Callback{
                    override fun onUserDeleted() {
                        Log.i("TEST_ACTIVITY", "DELETED_SUCCESFULLY")
                    }

                    override fun onError(errorBundle: ErrorBundle?) {
                        Log.i("TEST_ACTIVITY", "ERROR_WHILE_DELETION")
                    }

                }

                deleteUserDetailUseCase.execute("-MEYGoRyKodPu5MEEaqk", deleteUserCallback);
            }

        }
    }

}