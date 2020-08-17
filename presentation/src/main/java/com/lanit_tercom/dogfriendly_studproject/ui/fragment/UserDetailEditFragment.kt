package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.photo.PhotoStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.PhotoRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailEditPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailEditView
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.interactor.photo.impl.PushPhotoUseCaseImpl
import com.lanit_tercom.domain.interactor.user.EditUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.impl.EditUserDetailsUseCaseImpl
import com.lanit_tercom.domain.repository.PhotoRepository
import com.lanit_tercom.domain.repository.UserRepository
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class UserDetailEditFragment(private val user: UserModel?): BaseFragment(), UserDetailEditView {
    //Декларация UI элементов и переменных
    private lateinit var editName: TextInputEditText
    private lateinit var editAge: TextInputEditText
    private lateinit var avatar: ImageView
    private var userDetailEditPresenter: UserDetailEditPresenter? = null
    private var avatarUri: Uri? = null
    private val mapper = UserDtoModelMapper()

    //Инициализация презентера
    override fun initializePresenter() {
        val threadExecutor: ThreadExecutor = JobExecutor.getInstance()
        val postExecutionThread: PostExecutionThread = UIThread.getInstance()
        val networkManager: NetworkManager = NetworkManagerImpl(context)

        val userEntityStoreFactory = UserEntityStoreFactory(networkManager, null)
        val photoStoreFactory = PhotoStoreFactory(networkManager)
        val userEntityDtoMapper = UserEntityDtoMapper()


        val userRepository: UserRepository = UserRepositoryImpl.getInstance(userEntityStoreFactory,
                userEntityDtoMapper)
        val photoRepository: PhotoRepository = PhotoRepositoryImpl.getInstance(photoStoreFactory)

        val editUserDetailsUseCase: EditUserDetailsUseCase = EditUserDetailsUseCaseImpl(userRepository,
                threadExecutor, postExecutionThread)
        val pushPhotoUseCase = PushPhotoUseCaseImpl(photoRepository, threadExecutor, postExecutionThread)


        this.userDetailEditPresenter = UserDetailEditPresenter(editUserDetailsUseCase, pushPhotoUseCase)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_user_detail_edit, container, false)

        editName = view.findViewById(R.id.edit_name)
        editAge = view.findViewById(R.id.edit_age)
        avatar = view.findViewById(R.id.user_avatar)

        avatar.setOnClickListener{ loadAvatar() }

        view.findViewById<ImageView>(R.id.back_button).setOnClickListener { activity?.onBackPressed() }

        //Изменяем модельку юзера, пушим ее в базу данных и возвращаеся обратно в экран юзера
        view.findViewById<Button>(R.id.ready_button).setOnClickListener {

            //Валидатор введенных (или не введенных) значений.
            fun validate(): Boolean{
                if(editAge.text.isNullOrEmpty() || editAge.text.toString().intOrString() is String){
                    Toast.makeText(context, "Возраст введен не корректно!", Toast.LENGTH_SHORT).show()
                    return false
                }
                if(editName.text.isNullOrEmpty()){
                    Toast.makeText(context, "Введите свое имя!", Toast.LENGTH_SHORT).show()
                    return false
                }
                return true
            }

            if(validate()){
                user?.name = editName.text.toString()
                user?.age = editAge.text.toString().toInt()
                user?.avatar = avatarUri
                userDetailEditPresenter?.editUserDetails(user)
                activity?.onBackPressed()
            }
        }

        return view
    }

    //Проверка на то является ли строка числом
    private fun String.intOrString() = toIntOrNull() ?: this

    //Загрузка/создание/обрезание аватара
    private fun loadAvatar() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAspectRatio(1,1)
                .setRequestedSize(320, 320)
                .setActivityTitle("")
                .start(context!!, this);
    }

    //Обратная связь с галлереей
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                userDetailEditPresenter?.pushPhoto(user?.id+"/avatar", resultUri.toString());
                avatarUri = resultUri
                Glide.with(this)
                        .load(avatarUri)
                        .circleCrop()
                        .into(avatar);

            }
//            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) { }
        }

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(message: String) {

    }

}