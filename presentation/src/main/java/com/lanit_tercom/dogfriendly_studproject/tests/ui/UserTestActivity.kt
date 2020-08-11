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
import com.lanit_tercom.domain.interactor.user.CreateUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.GetUsersDetailsUseCase
import com.lanit_tercom.domain.interactor.user.impl.CreateUserDetailsUseCaseImpl
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
        button_get.setOnClickListener(this)
        button_add.setOnClickListener(this)
        button_delete.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

        when (p0?.id) {
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
            R.id.button_add ->{
                val user = UserDto()
                val petList = ArrayList<PetDto>()
                val pet = PetDto()

                pet.id = "1"
                pet.age = 2
                pet.about="ofrmkfjdjke"

                petList.add(pet)


                user.id = "1"
                user.name = "карасик"
                user.age = 21
                user.avatar = "qweqweqwe"
                user.about = "ewqewqewq"
                user.plans = "qweewqqwe"
                user.pets = petList



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
            R.id.button_delete ->{

                //If get and add works, let's assume that delete works too


            }

        }
    }

}