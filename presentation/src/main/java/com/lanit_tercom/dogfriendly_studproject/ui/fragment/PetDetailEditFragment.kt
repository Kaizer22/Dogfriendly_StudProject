package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.photo.PhotoStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.PhotoRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.PetDetailEditPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.PetDetailEditView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.BaseActivity
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.interactor.photo.impl.DeletePhotoUseCaseImpl
import com.lanit_tercom.domain.interactor.photo.impl.PushPhotoUseCaseImpl
import com.lanit_tercom.domain.interactor.user.DeletePetUseCase
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.impl.DeletePetUseCaseImpl
import com.lanit_tercom.domain.interactor.user.impl.GetUserDetailsUseCaseImpl
import com.lanit_tercom.domain.repository.PhotoRepository
import com.lanit_tercom.domain.repository.UserRepository
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class PetDetailEditFragment(private val userId: String?, override var pet: PetModel = PetModel()) : BaseFragment(), PetDetailEditView {
    private lateinit var editPetName: TextInputEditText
    private lateinit var editPetBreed: TextInputEditText
    private lateinit var editPetAge: TextInputEditText
    private lateinit var avatar: ImageView
    private lateinit var menButton: MaterialButton
    private lateinit var womanButton: MaterialButton
    private var petDetailEditPresenter: PetDetailEditPresenter? = null
    private var avatarUri: Uri? = null
    private var gender: String? = null

    override fun initializePresenter() {
        val threadExecutor: ThreadExecutor = JobExecutor.getInstance()
        val postExecutionThread: PostExecutionThread = UIThread.getInstance()
        val networkManager: NetworkManager = NetworkManagerImpl(context)
        val userEntityStoreFactory = UserEntityStoreFactory(networkManager, null)
        val userEntityDtoMapper = UserEntityDtoMapper()
        val userRepository: UserRepository = UserRepositoryImpl.getInstance(userEntityStoreFactory,
                userEntityDtoMapper)
        val getUserDetailsUseCase: GetUserDetailsUseCase = GetUserDetailsUseCaseImpl(userRepository,
                threadExecutor, postExecutionThread)

        petDetailEditPresenter = PetDetailEditPresenter(userId, pet, getUserDetailsUseCase)
    }

    //Отображает скачанную презентером модель
    fun initializeView(pet: PetModel?) {
        if(pet!=null){
            if (pet.name != null) {
                editPetName.setText(pet.name)
                this.pet.name = pet.name
            }
            if (pet.breed != null) {
                editPetBreed.setText(pet.breed)
                this.pet.breed = pet.breed
            }
            if (pet.age != null) {
                editPetAge.setText(pet.age.toString())
                this.pet.age = pet.age
            }
            if (pet.avatar != null) {
                Glide.with(this)
                        .load(pet.avatar)
                        .circleCrop()
                        .into(avatar)
                this.pet.avatar = pet.avatar
            }
        }
    }

    //Lifecycle методы
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pet_detail_edit, container, false)
        editPetName = view.findViewById(R.id.edit_pet_name)
        editPetBreed = view.findViewById(R.id.edit_pet_breed)
        editPetAge = view.findViewById(R.id.edit_pet_age)

        avatar = view.findViewById(R.id.pet_avatar)
        avatar.setOnClickListener { loadAvatar() }

        view.findViewById<ConstraintLayout>(R.id.main_layout).setOnClickListener { hideKeyboard() }

        view.findViewById<ImageView>(R.id.back_button).setOnClickListener { activity?.onBackPressed() }

        //Присвоили модельке питомца что надо и пошли дальше в характеры
        view.findViewById<Button>(R.id.ready_button).setOnClickListener {

            //Валидатор введенных (или не введенных) значений.
            fun validate(): Boolean {
                if (editPetAge.text.isNullOrEmpty() || editPetAge.text.toString().intOrString() is String) {
                    Toast.makeText(context, "Возраст введен не корректно!", Toast.LENGTH_SHORT).show()
                    return false
                }
                if (gender.isNullOrEmpty()) {
                    Toast.makeText(context, "Не выбран пол питомца!", Toast.LENGTH_SHORT).show()
                    return false
                }
                if (editPetName.text.toString() == "") {
                    Toast.makeText(context, "У питомца должно быть имя!", Toast.LENGTH_SHORT).show()
                    return false
                }
                return true
            }

            if (validate()) {
                pet.id = FirebaseDatabase.getInstance().reference.child("Users").child(userId!!).child("pets").push().key
                pet.name = editPetName.text.toString()
                pet.age = editPetAge.text.toString().toInt()
                pet.breed = editPetBreed.text.toString()
                pet.gender = gender
                pet.avatar = avatarUri
                navigateToNext(pet)
            }
        }

        womanButton = view.findViewById(R.id.woman_button)
        womanButton.setOnClickListener { setGender("woman") }

        menButton = view.findViewById(R.id.men_button)
        menButton.setOnClickListener { setGender("men") }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        petDetailEditPresenter?.setView(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        petDetailEditPresenter?.loadPetDetails()
    }

    override fun onResume() {
        super.onResume()
        editPetName.text?.clear()
        editPetAge.text?.clear()
        editPetBreed.text?.clear()
    }

    private fun hideKeyboard() {
        val inputMethodManager: InputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    //Проверка на то является ли строка числом
    private fun String.intOrString() = toIntOrNull() ?: this

    //Обработка нажатий кнопо выбора пола
    private fun setGender(gender: String) {
        if (gender == "men") {
            menButton.background.setTint(Color.parseColor("#B2BC24"))
            menButton.setTextColor(Color.parseColor("#FFFFFF"))
            womanButton.background.setTint(Color.parseColor("#FEFFF0"))
            womanButton.setTextColor(Color.parseColor("#94A604"))
            this.gender = "men"
        } else {
            womanButton.background.setTint(Color.parseColor("#B2BC24"))
            womanButton.setTextColor(Color.parseColor("#FFFFFF"))
            menButton.background.setTint(Color.parseColor("#FEFFF0"))
            menButton.setTextColor(Color.parseColor("#94A604"))
            this.gender = "woman"
        }
    }

    //Загрузка/создание/обрезание аватара
    private fun loadAvatar() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(1, 1)
                .setActivityTitle("")
                .start(context!!, this)
    }

    //Обратная связь с галереей, проброс данных обратно в UserDetailActivity(4)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                avatarUri = resultUri
                avatar.setImageURI(avatarUri)
            }
//            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {}
        }

    }

    override fun navigateToNext(pet: PetModel) {
        (activity as BaseActivity).replaceFragment(R.id.ft_container, PetCharacterFragment(userId, pet))
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