package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.text.InputFilter
import android.view.*
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
import com.lanit_tercom.dogfriendly_studproject.mvp.view.EditTextView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.MainNavigationActivity
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.interactor.user.EditUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.impl.EditUserDetailsUseCaseImpl
import com.lanit_tercom.domain.repository.UserRepository
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl

class EditTextFragment(private val fieldType: String?, private val user: UserModel?): BaseFragment(), EditTextView {
    private lateinit var btnReady: Button
    private lateinit var btnBack: ImageButton
    private lateinit var titleText: TextView
    private lateinit var editText: EditText
    private var editTextPresenter: EditTextPresenter? = null
    private var originalMode : Int? = null

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

        this.editTextPresenter = EditTextPresenter(editUserDetailsUseCase)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextPresenter?.setView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        originalMode = activity?.window?.getSoftInputMode()
        activity?.window?.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_text, container, false)
        btnReady = view.findViewById(R.id.ready_button)
        btnBack = view.findViewById(R.id.back_button)
        editText = view.findViewById(R.id.edit_text)
        titleText = view.findViewById(R.id.title_text)

        view.findViewById<ConstraintLayout>(R.id.main_layout).setOnClickListener { hideKeyboard() }

        when(fieldType){
            "plans" -> {
                editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(150))
                editText.hint="Напиши здесь о своих планах на ближайшую прогулку"
                titleText.text="Планы на прогулку"
                if(user?.plans != null) editText.setText(user.plans)
            }
            "about" -> {
                editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(200))
                editText.hint="Расскажите о своем питомце"
                titleText.text="О себе"
                if(user?.about != null) editText.setText(user.about)
            }
        }

        btnBack.setOnClickListener { activity?.onBackPressed() }

        btnReady.setOnClickListener {
            when(fieldType){
                "plans" -> user?.plans = editText.text.toString()
                "about" -> user?.about = editText.text.toString()
            }
            editTextPresenter?.editTextFields(user)

            hideKeyboard()
            //activity?.onBackPressed()
        }

        return view

    }

    private fun Window.getSoftInputMode() : Int {
        return attributes.softInputMode
    }

    //Прячем клавиатуру
    private fun hideKeyboard() {
        val inputMethodManager: InputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        originalMode?.let { activity?.window?.setSoftInputMode(it) }
    }

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