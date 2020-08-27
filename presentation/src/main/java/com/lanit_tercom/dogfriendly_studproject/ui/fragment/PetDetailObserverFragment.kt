package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.ChannelCache
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.UserCache
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.firebase.photo.PhotoStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.mapper.ChannelEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.ChannelRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.data.repository.PhotoRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.PetDetailObserverPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.PetDetailObserverView
import com.lanit_tercom.dogfriendly_studproject.mvp.view.PetDetailView
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.Character
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.CharacterAdapter
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.PhotoAdapter
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.interactor.channel.AddChannelUseCase
import com.lanit_tercom.domain.interactor.channel.impl.AddChannelUseCaseImpl
import com.lanit_tercom.domain.interactor.photo.DeletePhotoUseCase
import com.lanit_tercom.domain.interactor.photo.impl.DeletePhotoArrayUseCaseImpl
import com.lanit_tercom.domain.interactor.photo.impl.DeletePhotoUseCaseImpl
import com.lanit_tercom.domain.interactor.user.DeletePetUseCase
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.impl.DeletePetUseCaseImpl
import com.lanit_tercom.domain.interactor.user.impl.GetUserDetailsUseCaseImpl
import com.lanit_tercom.domain.repository.ChannelRepository
import com.lanit_tercom.domain.repository.PhotoRepository
import com.lanit_tercom.domain.repository.UserRepository
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl

class PetDetailObserverFragment(private val userId: String,
        private val pet: PetModel) : BaseFragment(), PetDetailObserverView {
    private lateinit var avatar: ImageView
    private lateinit var name: TextView
    private lateinit var description: TextView
    private lateinit var aboutText: TextView
    private lateinit var characterList: RecyclerView
    private lateinit var photoList: RecyclerView
    private lateinit var genderImage: ImageView

    private lateinit var startChannel: ImageButton

    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var photoAdapter: PhotoAdapter

    private lateinit var petDetailObserverPresenter: PetDetailObserverPresenter


    private fun ageDesc(age: Int?): String{
        if(age == null) return ""
        val ageLastNumber = age % 10
        val exclusion = age % 100 in 11..14
        var old = ""

        if (ageLastNumber == 1) old = "год"
        else if (ageLastNumber == 0 || ageLastNumber in 5..9) old = "лет"
        else if (ageLastNumber in 2..4) old = "года"
        if (exclusion) old = "лет"
        return "$age $old"
    }

    fun initialize(pet: PetModel){
        if(pet.name != null)
            name.text = pet.name

        var desc = ""
        if(pet.breed != null) desc += pet.breed
        if(pet.age != null) desc += ", "+ ageDesc(pet.age)
        if(desc.startsWith(", ")) {
            description.text = desc.substring(2)
        } else {
            description.text = desc
        }

        if(pet.avatar != null){
            Glide.with(this)
                    .load(pet.avatar)
                    .into(avatar)
        }

        if(pet.about != null){
            aboutText.text = pet.about
        }

        if(pet.gender != null){
            if(pet.gender == "men"){
                genderImage.setImageResource(R.drawable.ic_male)
            } else {
                genderImage.setImageResource(R.drawable.ic_female)
            }
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pet_detail_observer, container, false)

        avatar = view.findViewById(R.id.pet_avatar)
        name = view.findViewById(R.id.name)
        description = view.findViewById(R.id.description)
        aboutText = view.findViewById(R.id.about_text)
        genderImage = view.findViewById(R.id.gender_image)

        startChannel = view.findViewById(R.id.button_start_chat)
        startChannel.setOnClickListener { petDetailObserverPresenter.addChannel(petDetailObserverPresenter.currentUserId,
                userId) }

        characterList = view.findViewById(R.id.character_list)
        photoList = view.findViewById(R.id.photo_list)


        val characters = ArrayList<Character>()
        if(pet.character != null){
            for(character in pet.character!!){
                when(character){

                    "active" -> characters.add(Character("Активная", R.drawable.active))
                    "naturalist" -> characters.add(Character("Натуралист", R.drawable.naturalist))
                    "sqirell_hater" -> characters.add(Character("Гроза белок", R.drawable.sqirell_hater))
                    "searcher" -> characters.add(Character("Ищет клады", R.drawable.searcher))

                    "gourmet" -> characters.add(Character("Гурман", R.drawable.gourmet))
                    "cat_lover" -> characters.add(Character("Кошатник", R.drawable.cat_lover))
                    "shy" -> characters.add(Character("Стесняшка", R.drawable.shy))
                    "swimmer" -> characters.add(Character("Пловец", R.drawable.swimmer))

                    "sherlock" -> characters.add(Character("Шерлок", R.drawable.sherlock))
                    "fighter" -> characters.add(Character("Драчун", R.drawable.fighter))
                    "fidget" -> characters.add(Character("Непоседа", R.drawable.fidget))
                    "angel" -> characters.add(Character("Ангел", R.drawable.angel))

                }
            }
        }


        characterAdapter = CharacterAdapter(characters)
        characterList.adapter = characterAdapter
        characterList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        characterAdapter.notifyDataSetChanged()

        if(pet.photos != null){
            photoAdapter = PhotoAdapter(pet.photos!!)
            photoList.adapter = photoAdapter
            photoList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            photoAdapter.notifyDataSetChanged()
        }


        initialize(pet)

        return view
    }

    override fun initializePresenter() {
        val threadExecutor: ThreadExecutor = JobExecutor.getInstance()
        val postExecutionThread: PostExecutionThread = UIThread.getInstance()
        val networkManager: NetworkManager = NetworkManagerImpl(context)
        val channelCache: ChannelCache? = null
        val channelStoreFactory = ChannelEntityStoreFactory(networkManager, channelCache)

        val userCache: UserCache? = null
        val userStoreFactory = UserEntityStoreFactory(networkManager, userCache)

        val channelEntityDtoMapper = ChannelEntityDtoMapper()
        val channelRepository: ChannelRepository = ChannelRepositoryImpl.getInstance(channelStoreFactory,
        channelEntityDtoMapper)

        val userEntityDtoMapper = UserEntityDtoMapper()
        val userRepository: UserRepository = UserRepositoryImpl.getInstance(userStoreFactory,
                userEntityDtoMapper)
        //val channelEntityStoreFactory = ChannelEntityStoreFactory(networkManager, null)


        val addChannel: AddChannelUseCase = AddChannelUseCaseImpl(channelRepository,
                threadExecutor, postExecutionThread)
        val getUserDetails: GetUserDetailsUseCase = GetUserDetailsUseCaseImpl(userRepository,
                threadExecutor, postExecutionThread)

        petDetailObserverPresenter = PetDetailObserverPresenter(addChannel, getUserDetails) }
}


