package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.photo.PhotoStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.PhotoRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.PetPhotoPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.PetDetailEditView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.UserDetailActivity
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.interactor.photo.DeletePhotoUseCase
import com.lanit_tercom.domain.interactor.photo.PushPhotoArrayUseCase
import com.lanit_tercom.domain.interactor.photo.PushPhotoUseCase
import com.lanit_tercom.domain.interactor.photo.impl.DeletePhotoUseCaseImpl
import com.lanit_tercom.domain.interactor.photo.impl.PushPhotoArrayUseCaseImpl
import com.lanit_tercom.domain.interactor.photo.impl.PushPhotoUseCaseImpl
import com.lanit_tercom.domain.interactor.user.AddPetUseCase
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.impl.AddPetUseCaseImpl
import com.lanit_tercom.domain.interactor.user.impl.GetUserDetailsUseCaseImpl
import com.lanit_tercom.domain.repository.PhotoRepository
import com.lanit_tercom.domain.repository.UserRepository
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class PetPhotoFragment(private val userId: String?): BaseFragment(), PetDetailEditView {
    private lateinit var elements: ArrayList<Pair<ImageView, ImageView>>
    private var petPhotoPresenter: PetPhotoPresenter? = null
    private var nextImageSpace: Int = 0
    private lateinit var pet: PetModel
    private var photos: Array<String> = Array(8) {"0"}
    private val emptyPhoto = Uri.parse("android.resource://com.lanit_tercom.dogfriendly_studproject.ui.activity.pet_detail/" + R.drawable.ic_button_add_photo)

    fun initializePet(pet: PetModel){
        this.pet = pet
    }

    //Инициализация презентера
    override fun initializePresenter() {
        val threadExecutor: ThreadExecutor = JobExecutor.getInstance()
        val postExecutionThread: PostExecutionThread = UIThread.getInstance()
        val networkManager: NetworkManager = NetworkManagerImpl(context)
        val userEntityStoreFactory = UserEntityStoreFactory(networkManager, null)
        val photoStoreFactory = PhotoStoreFactory(networkManager)
        val userEntityDtoMapper = UserEntityDtoMapper()

        val photoRepository: PhotoRepository = PhotoRepositoryImpl.getInstance(photoStoreFactory)
        val userRepository: UserRepository = UserRepositoryImpl.getInstance(userEntityStoreFactory,
                userEntityDtoMapper)

        val addPetUseCase: AddPetUseCase = AddPetUseCaseImpl(userRepository,
                threadExecutor, postExecutionThread)
        val deletePhotoUseCase: DeletePhotoUseCase = DeletePhotoUseCaseImpl(photoRepository, threadExecutor, postExecutionThread)
        val pushPhotoArrayUseCase: PushPhotoArrayUseCase = PushPhotoArrayUseCaseImpl(photoRepository, threadExecutor, postExecutionThread)
        val pushPhotoUseCase: PushPhotoUseCase = PushPhotoUseCaseImpl(photoRepository, threadExecutor, postExecutionThread)
        this.petPhotoPresenter = PetPhotoPresenter(addPetUseCase, deletePhotoUseCase, pushPhotoUseCase, pushPhotoArrayUseCase)
    }

    //Lifecycle-методы
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        petPhotoPresenter?.setView(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        petPhotoPresenter?.initialize(userId, pet.id)
    }

    override fun onPause() {
        super.onPause()
        petPhotoPresenter?.onPause()
    }

    override fun onResume() {
        super.onResume()
        petPhotoPresenter?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        petPhotoPresenter?.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pet_photo, container, false)

        photos = Array(8) {"0"}
        nextImageSpace = 0

        if(pet.photos!=null)
            for(photo in pet.photos!!)
                setPhoto(elements, nextImageSpace++, photo)

        elements = initialize(view)
        view.findViewById<ImageView>(R.id.photo_image).setOnClickListener {
            if (nextImageSpace != 8)
                loadPhoto()
        }

        view.findViewById<ImageView>(R.id.back_button).setOnClickListener {
            activity?.onBackPressed()
        }

        //Преобразуем array в arrayList и добавляем питомца. По завершении презентер вызовет navigateToNext()
        view.findViewById<Button>(R.id.ready_button).setOnClickListener {
            val photoList = ArrayList<String>()
            for(photo in photos) if(photo != "0") photoList.add(photo)
            petPhotoPresenter?.addPet(pet, photoList)
        }
        return view
    }

    //Инициализация элементов, присвоение им OnClickListener
    private fun initialize(view: View) : ArrayList<Pair<ImageView, ImageView>>{
        val output = ArrayList<Pair<ImageView, ImageView>>()
        output.add(Pair(view.findViewById(R.id.photo1),view.findViewById(R.id.remove_photo1)))
        output.add(Pair(view.findViewById(R.id.photo2),view.findViewById(R.id.remove_photo2)))
        output.add(Pair(view.findViewById(R.id.photo3),view.findViewById(R.id.remove_photo3)))
        output.add(Pair(view.findViewById(R.id.photo4),view.findViewById(R.id.remove_photo4)))
        output.add(Pair(view.findViewById(R.id.photo5),view.findViewById(R.id.remove_photo5)))
        output.add(Pair(view.findViewById(R.id.photo6),view.findViewById(R.id.remove_photo6)))
        output.add(Pair(view.findViewById(R.id.photo7),view.findViewById(R.id.remove_photo7)))
        output.add(Pair(view.findViewById(R.id.photo8),view.findViewById(R.id.remove_photo8)))

        for(i in 0..6) output[i].second.setOnClickListener{deleteMiddle(i)}
        output[7].second.setOnClickListener {deleteLast()}

        for(element in output) element.second.visibility = View.INVISIBLE

        return output
    }

    //Удалить элемент не находящийся в конце
    private fun deleteMiddle(i: Int){
        for(j in (i..6)){
            elements[j].first.setImageDrawable(elements[j+1].first.drawable)
            photos[j]=photos[j+1]
        }
        elements[7].first.setImageURI(emptyPhoto)
        nextImageSpace--
        elements[nextImageSpace].second.visibility = View.INVISIBLE
    }

    //Удалить последний элемент
    private fun deleteLast(){
        photos[7] = "0"
        elements[7].first.setImageURI(emptyPhoto)
        elements[7].second.visibility = View.INVISIBLE
        nextImageSpace--

    }

    //Вставить фото в элемент
    private fun setPhoto(elements: ArrayList<Pair<ImageView, ImageView>>, position: Int, image: Uri){
        Glide.with(this).load(image).into(elements[position].first)
        elements[position].second.visibility = View.VISIBLE
    }

    //Загрузка/создание/обрезание аватара
    private fun loadPhoto() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(1,1)
                .setActivityTitle("")
                .start(context!!,this)
    }

    //Обратная связь с галереей
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                if(nextImageSpace != 8){
                    photos[nextImageSpace] = resultUri.toString()
                    setPhoto(elements, nextImageSpace++, resultUri)

                }
            }
//            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) { }
        }
    }

    override fun navigateToNext(pet: PetModel) {
        (activity as UserDetailActivity).startUserDetail()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(message: String) {

    }


}