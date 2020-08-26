package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.mapper.ChannelEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.ChannelRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailObserverPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.UserDetailObserverActivity
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.PetListAdapter
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.interactor.channel.AddChannelUseCase
import com.lanit_tercom.domain.interactor.channel.impl.AddChannelUseCaseImpl
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.impl.GetUserDetailsUseCaseImpl
import com.lanit_tercom.domain.repository.ChannelRepository
import com.lanit_tercom.domain.repository.UserRepository
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl
import kotlinx.android.synthetic.main.fragment_user_detail_observer.*

class UserDetailObserverFragment(private val hostId: String?,
                                 private val userId: String?) : BaseFragment(), UserDetailView{
    private lateinit var petList: RecyclerView
    private lateinit var plansText: TextView
    private lateinit var aboutText: TextView
    private lateinit var petListAdapter: PetListAdapter
    private lateinit var name: TextView
    private lateinit var age: TextView
    private lateinit var avatar: ImageView
    private var pets: ArrayList<UserDetailFragment.PetListItem> = ArrayList()
    private var userDetailObserverPresenter: UserDetailObserverPresenter? = null
    private var user: UserModel? = null

    //Инициализация презентера
    override fun initializePresenter() {
        val threadExecutor: ThreadExecutor = JobExecutor.getInstance()
        val postExecutionThread: PostExecutionThread = UIThread.getInstance()
        val networkManager: NetworkManager = NetworkManagerImpl(context)

        val userEntityStoreFactory = UserEntityStoreFactory(networkManager, null)
        val channelEntityStoreFactory = ChannelEntityStoreFactory(networkManager, null)

        val userEntityDtoMapper = UserEntityDtoMapper()
        val channelEntityDtoMapper = ChannelEntityDtoMapper()
        val userRepository: UserRepository = UserRepositoryImpl.getInstance(userEntityStoreFactory,
                userEntityDtoMapper)
        val channelRepository: ChannelRepository = ChannelRepositoryImpl.getInstance(channelEntityStoreFactory,
        channelEntityDtoMapper)
        val getUserDetailsUseCase: GetUserDetailsUseCase = GetUserDetailsUseCaseImpl(userRepository,
                threadExecutor, postExecutionThread)
        val addChannelUseCase: AddChannelUseCase = AddChannelUseCaseImpl(channelRepository,
                threadExecutor, postExecutionThread)

        userDetailObserverPresenter = UserDetailObserverPresenter(getUserDetailsUseCase,
                addChannelUseCase)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_user_detail_observer, container, false)

        //Инициализация списка питомцев
        petList = view.findViewById(R.id.pet_list)
        petList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        petListAdapter = PetListAdapter(pets)
        petList.adapter = petListAdapter

        name = view.findViewById(R.id.name)
        age = view.findViewById(R.id.age)
        avatar = view.findViewById(R.id.user_avatar)

        view.findViewById<ImageButton>(R.id.button_start_chat).setOnClickListener{
            userDetailObserverPresenter?.startChatWithCurrentUser(hostId, userId, user?.name)
            (activity as UserDetailObserverActivity).navigateToChannelList(AuthManagerFirebaseImpl().currentUserId)
        }

        view.findViewById<ImageButton>(R.id.edit_button).setOnClickListener {
            val dialog = Dialog(context!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            dialog.setContentView(R.layout.dialog_user_detail_observer)
            val btnHide: View = dialog.findViewById(R.id.linearLayout2)
            val btnReport: View = dialog.findViewById(R.id.linearLayout)
            val btnBlackList: View = dialog.findViewById(R.id.linearLayout3)

            btnHide.setOnClickListener{/*todo*/}
            btnReport.setOnClickListener {/*todo*/}
            btnBlackList.setOnClickListener {/*todo*/}

            dialog.show()
        }

        //Динамическое задание высоты блока
        val appbar = view.findViewById<View>(R.id.top_panel) as ConstraintLayout
        val heightDp = resources.displayMetrics.heightPixels * 0.5 -  resources.displayMetrics.density
        val lp = appbar.layoutParams as LinearLayout.LayoutParams
        lp.height = heightDp.toInt()

        plansText = view.findViewById(R.id.plans_text)
        aboutText = view.findViewById(R.id.about_text)

        return view
    }

    private fun startChat() {

    }


    //Lifecycle-методы
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDetailObserverPresenter?.setView(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userDetailObserverPresenter?.initialize(userId)
    }

    override fun onPause() {
        super.onPause()
        userDetailObserverPresenter?.onPause()
    }

    override fun onResume() {
        super.onResume()
        userDetailObserverPresenter?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        userDetailObserverPresenter?.onDestroy()
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

    //Функции для вывода данных на экран
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

    private fun getPetListItem(petModel: PetModel): UserDetailFragment.PetListItem {
        return UserDetailFragment.PetListItem(petModel.id,
                petModel.avatar.toString(),
                petModel.name,
                petModel.age.toString() + " ," + petModel.breed)
    }


    override fun renderCurrentUser(user: UserModel?) {
        this.user = user

        name.text = user?.name
        age.text = ageDesc(user?.age)
        plansText.text = user?.plans
        aboutText.text = user?.about

        if(user?.avatar != null){
            Glide.with(this)
                    .load(user.avatar)
                    .circleCrop()
                    .into(avatar)
        }

        val petModelList = user?.pets
        if (petModelList != null) {
            for(model in petModelList){
                val item = getPetListItem(model)
                if(!pets.contains(item)) pets.add(item)
            }
        }

        petListAdapter.notifyDataSetChanged()
    }

}