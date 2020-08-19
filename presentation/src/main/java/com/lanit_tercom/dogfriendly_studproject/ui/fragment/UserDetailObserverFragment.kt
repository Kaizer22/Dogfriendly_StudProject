package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailObserverPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailView
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.PetListAdapter
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.impl.GetUserDetailsUseCaseImpl
import com.lanit_tercom.domain.repository.UserRepository
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl

class UserDetailObserverFragment(private val userId: String?) : BaseFragment(), UserDetailView{
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
        val userEntityDtoMapper = UserEntityDtoMapper()
        val userRepository: UserRepository = UserRepositoryImpl.getInstance(userEntityStoreFactory,
                userEntityDtoMapper)
        val getUserDetailsUseCase: GetUserDetailsUseCase = GetUserDetailsUseCaseImpl(userRepository,
                threadExecutor, postExecutionThread)

        userDetailObserverPresenter = UserDetailObserverPresenter(getUserDetailsUseCase)
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

        //Динамическое задание высоты блока
        val appbar = view.findViewById<View>(R.id.appbar) as AppBarLayout
        val heightDp = resources.displayMetrics.heightPixels * 0.5 -  resources.displayMetrics.density
        val lp = appbar.layoutParams as CoordinatorLayout.LayoutParams
        lp.height = heightDp.toInt()

        plansText = view.findViewById(R.id.plans_text)
        aboutText = view.findViewById(R.id.about_text)

        return view
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