package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.app.Activity
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
import androidx.constraintlayout.widget.ConstraintLayout
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.EditTextPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailEditPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.EditTextView
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailEditView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.BaseActivity
import com.lanit_tercom.dogfriendly_studproject.ui.activity.MainNavigationActivity
import com.lanit_tercom.dogfriendly_studproject.ui.activity.UserDetailActivity
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.interactor.user.EditUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.impl.EditUserDetailsUseCaseImpl
import com.lanit_tercom.domain.interactor.user.impl.GetUserDetailsUseCaseImpl
import com.lanit_tercom.domain.repository.UserRepository
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl

class EditTextFragment(private val fieldType: String?, private val userId: String?): BaseFragment(), EditTextView {
    private lateinit var btnReady: Button
    private lateinit var btnBack: ImageButton
    private lateinit var titleText: TextView
    private lateinit var editText: EditText
    private lateinit var editTextPresenter: EditTextPresenter
    private var prevValue: String? = null

    fun setPrevValue(prevValue: String?){
        this.prevValue = prevValue
    }

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
        val getUserDetailsUseCase = GetUserDetailsUseCaseImpl(userRepository, threadExecutor, postExecutionThread)

        this.editTextPresenter = EditTextPresenter(getUserDetailsUseCase,editUserDetailsUseCase)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_text, container, false)
        //Инициализация элементов экрана
        btnReady = view.findViewById(R.id.ready_button)
        btnBack = view.findViewById(R.id.back_button)
        editText = view.findViewById(R.id.editText)
        titleText = view.findViewById(R.id.title_text)

        view.findViewById<ConstraintLayout>(R.id.main_layout).setOnClickListener { hideKeyboard() }

        //Загрузка текущего значения (если имеется)
        if(prevValue != null) editText.setText(prevValue)

        //Установка правильной подсказки и текста сверху, ограничение длины текста
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
            val user = editTextPresenter.user

            when(fieldType){
                "plans" -> {
                    user?.plans = editText.text.toString()
                }
                "about" -> {
                    user?.about = editText.text.toString()
                }
            }

            editTextPresenter.editTextFields()

            //Прячем клавиатуру при выходе из метода
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
            //activity?.onBackPressed()
        }

        return view

    }

    //Прячем клавиатуру
    private fun hideKeyboard() {
        val inputMethodManager: InputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    //Lifecycle - методы
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextPresenter.setView(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        editTextPresenter.initialize(userId)
    }


    //Навигация обратно в профиль пользователя
    override fun navigateBack() {
        (activity as MainNavigationActivity).startUserDetail()
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showError(message: String) {
        TODO("Not yet implemented")
    }


}