package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.photo.PhotoStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.PhotoRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.SwipeHelper
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.SwipeHelper.UnderlayButtonClickListener
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.BaseActivity
import com.lanit_tercom.dogfriendly_studproject.ui.activity.MainNavigationActivity
import com.lanit_tercom.dogfriendly_studproject.ui.activity.UserDetailActivity
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.PetListAdapter
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.interactor.photo.DeletePhotoArrayUseCase
import com.lanit_tercom.domain.interactor.photo.DeletePhotoUseCase
import com.lanit_tercom.domain.interactor.photo.impl.DeletePhotoArrayUseCaseImpl
import com.lanit_tercom.domain.interactor.photo.impl.DeletePhotoUseCaseImpl
import com.lanit_tercom.domain.interactor.user.DeletePetUseCase
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.impl.DeletePetUseCaseImpl
import com.lanit_tercom.domain.interactor.user.impl.GetUserDetailsUseCaseImpl
import com.lanit_tercom.domain.repository.PhotoRepository
import com.lanit_tercom.domain.repository.UserRepository
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl
import java.lang.Exception
import kotlin.math.abs

class UserDetailFragment(private val userId: String?) : BaseFragment(), UserDetailView{
    private lateinit var petList: RecyclerView
    private lateinit var plansText: TextView
    private lateinit var aboutText: TextView
    lateinit var petListAdapter: PetListAdapter
    private lateinit var name: TextView
    private lateinit var age: TextView
    private lateinit var avatar: ImageView
    var pets: ArrayList<PetListItem> = ArrayList()
    private var userDetailPresenter: UserDetailPresenter? = null
    private var user: UserModel? = null

    //Инициализация презентера
    override fun initializePresenter() {
        val threadExecutor: ThreadExecutor = JobExecutor.getInstance()
        val postExecutionThread: PostExecutionThread = UIThread.getInstance()
        val networkManager: NetworkManager = NetworkManagerImpl(context)
        val photoStoreFactory = PhotoStoreFactory(networkManager)

        val photoRepository: PhotoRepository = PhotoRepositoryImpl.getInstance(photoStoreFactory)
        val userEntityStoreFactory = UserEntityStoreFactory(networkManager, null)
        val userEntityDtoMapper = UserEntityDtoMapper()
        val userRepository: UserRepository = UserRepositoryImpl.getInstance(userEntityStoreFactory,
                userEntityDtoMapper)

        val getUserDetailsUseCase: GetUserDetailsUseCase = GetUserDetailsUseCaseImpl(userRepository,
                threadExecutor, postExecutionThread)
        val deletePetUseCase: DeletePetUseCase = DeletePetUseCaseImpl(userRepository,
                threadExecutor, postExecutionThread)
        val deletePhotoUseCase: DeletePhotoUseCase = DeletePhotoUseCaseImpl(photoRepository, threadExecutor, postExecutionThread)
        val deletePhotoArrayUseCase = DeletePhotoArrayUseCaseImpl(photoRepository, threadExecutor, postExecutionThread)

        userDetailPresenter = UserDetailPresenter(getUserDetailsUseCase, deletePetUseCase, deletePhotoUseCase, deletePhotoArrayUseCase)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_user_detail, container, false)

        //Инициализация списка питомцев
        petList = view.findViewById(R.id.pet_list)
        petList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        petListAdapter = PetListAdapter(pets)
        petList.adapter = petListAdapter

        name = view.findViewById(R.id.name)
        age = view.findViewById(R.id.age)
        avatar = view.findViewById(R.id.user_avatar)

        //Это чтобы кнопка удаления в RecyclerView выезжала, взято из гугла
        val swipeHelper: SwipeHelper = object : SwipeHelper(context, petList) {

            override fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder?, underlayButtons: ArrayList<UnderlayButton?>) {
                underlayButtons.add(UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3C30"),
                        UnderlayButtonClickListener {
                            if (viewHolder != null) {
                                val dialog = Dialog(context!!)
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

                                dialog.setContentView(R.layout.dialog_user_detail)
                                val btnCancel: Button = dialog.findViewById(R.id.cancel)
                                val btnDelete: Button = dialog.findViewById(R.id.delete)

                                btnCancel.setOnClickListener{dialog.dismiss()}
                                btnDelete.setOnClickListener {
                                    userDetailPresenter?.deletePet(pets[viewHolder.adapterPosition].id, viewHolder.adapterPosition)
                                    dialog.dismiss()
                                }

                                dialog.show()
                            }
                        }
                ))
            }
        }

        val bottomNav = (activity as BaseActivity).findViewById<View>(R.id.nav_view) as BottomNavigationView
        //Присвоение OnClickListener кнопкам
        view.findViewById<View>(R.id.edit_button).setOnClickListener { toUserEdit()
            bottomNav.visibility = View.GONE}
        view.findViewById<View>(R.id.add_pet_button).setOnClickListener { addPet()
            bottomNav.visibility = View.GONE}
        view.findViewById<View>(R.id.to_map_button).setOnClickListener { toMap() }
        view.findViewById<View>(R.id.to_chats_button).setOnClickListener { toChats() }
        view.findViewById<View>(R.id.to_settings_button).setOnClickListener { toSettings() }

        //Присвоение OnClickListener текстовым полям "о себе" и "планы на прогулку" - так будет открываться фрагмент для редактирования соотв полей
        plansText = view.findViewById(R.id.plans_text)
        plansText.setOnClickListener {
            (activity as MainNavigationActivity).startPlansEdit(plansText.text.toString())

        }

        aboutText = view.findViewById(R.id.about_text)
        aboutText.setOnClickListener {
            (activity as MainNavigationActivity).startAboutEdit(plansText.text.toString())
        }

        //Открытие/скрытие нижней панели

        view.findViewById<AppBarLayout>(R.id.appbar).addOnOffsetChangedListener(object : AppBarStateChangeListener() {

            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                if (state == State.EXPANDED)
                    bottomNav.visibility = View.GONE
                if (state == State.COLLAPSED)
                    bottomNav.visibility = View.VISIBLE
            }

        })

        return view
    }

    //Навигация
    private fun toUserEdit() {
        (activity as MainNavigationActivity).startUserDetailEdit()
    }

    private fun addPet() {
        (activity as MainNavigationActivity).startPetDetailEdit(PetModel())
    }

    private fun toPetDetail() {}

    private fun toMap() {}

    private fun toChats() {}

    private fun toSettings() {}

    //Lifecycle-методы
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDetailPresenter?.setView(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userDetailPresenter?.initialize(userId)
    }

    override fun onPause() {
        super.onPause()
        userDetailPresenter?.onPause()
    }

    override fun onResume() {
        super.onResume()
        userDetailPresenter?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        userDetailPresenter?.onDestroy()
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

    private fun getPetListItem(petModel: PetModel): PetListItem{
        return PetListItem(petModel.id,
                petModel.avatar.toString(),
                petModel.name,
                petModel.age.toString()+" ,"+petModel.breed)
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
        } else {
            Glide.with(this)
                    .load(R.drawable.ic_set_avatar_green)
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

    //Класс для реализации скрытия нижней панели
    abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {
        enum class State {
            EXPANDED, COLLAPSED, IDLE
        }

        private var mCurrentState = State.IDLE
        override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
            mCurrentState = when {
                i == 0 -> {
                    if (mCurrentState != State.EXPANDED) {
                        onStateChanged(appBarLayout, State.EXPANDED)
                    }
                    State.EXPANDED
                }
                abs(i) >= appBarLayout.totalScrollRange -> {
                    if (mCurrentState != State.COLLAPSED) {
                        onStateChanged(appBarLayout, State.COLLAPSED)
                    }
                    State.COLLAPSED
                }
                else -> {
                    if (mCurrentState != State.IDLE) {
                        onStateChanged(appBarLayout, State.IDLE)
                    }
                    State.IDLE
                }
            }
        }

        abstract fun onStateChanged(appBarLayout: AppBarLayout?, state: State?)
    }

    //Класс представляющий список питомцев юзера
    data class PetListItem(val id: String?, val uri: String?, val name: String?, val desc: String?)
}