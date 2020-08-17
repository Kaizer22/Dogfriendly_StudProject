package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailEditPresenter
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.interactor.user.EditUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.impl.EditUserDetailsUseCaseImpl
import com.lanit_tercom.domain.repository.UserRepository
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl

class EditTextFragment(private val fieldType: String?, private val user: UserModel?): BaseFragment() {
    private lateinit var btnReady: Button
    private lateinit var btnBack: ImageButton
    private lateinit var titleText: TextView
    private lateinit var editText: EditText
    private var userDetailEditPresenter: UserDetailEditPresenter? = null

    //Инициализация презентера
    override fun initializePresenter() {
        val threadExecutor: ThreadExecutor = JobExecutor.getInstance()
        val postExecutionThread: PostExecutionThread = UIThread.getInstance()
        val networkManager: NetworkManager = NetworkManagerImpl(context)
        val userEntityStoreFactory = UserEntityStoreFactory(networkManager, null)
        val userEntityDtoMapper = UserEntityDtoMapper()
        val userRepository: UserRepository = UserRepositoryImpl.getInstance(userEntityStoreFactory,
                userEntityDtoMapper)
        val editUserDetailsUseCase: EditUserDetailsUseCase = EditUserDetailsUseCaseImpl(userRepository,
                threadExecutor, postExecutionThread)

        this.userDetailEditPresenter = UserDetailEditPresenter(editUserDetailsUseCase, null, null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_edit_text, container, false)
        btnReady = view.findViewById(R.id.ready_button)
        btnBack = view.findViewById(R.id.back_button)
        editText = view.findViewById(R.id.editText)
        titleText = view.findViewById(R.id.title_text)

        when(fieldType){
            "plans" -> {
                editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(150))
                editText.hint="Напиши здесь о своих планах на ближайшую прогулку"
                titleText.text="Планы на прогулку"
            }
            "about" -> {
                editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(200))
                editText.hint="Расскажите о своем питомце"
                titleText.text="О себе"
            }
        }

        btnBack.setOnClickListener { activity?.onBackPressed() }

        btnReady.setOnClickListener {
            when(fieldType){
                "plans" -> user?.plans = editText.text.toString()
                "about" -> user?.about = editText.text.toString()
            }
            userDetailEditPresenter?.editUserDetails(user)

            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
            activity?.onBackPressed()
        }

        return view

    }


}