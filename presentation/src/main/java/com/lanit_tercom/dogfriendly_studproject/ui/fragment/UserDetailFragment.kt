package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.user.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.SwipeHelper
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailPresenter
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailView
import com.lanit_tercom.dogfriendly_studproject.ui.activity.BaseActivity
import com.lanit_tercom.dogfriendly_studproject.ui.activity.EditTextActivity
import com.lanit_tercom.dogfriendly_studproject.ui.adapter.PetListAdapter
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.interactor.user.DeletePetUseCase
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase
import com.lanit_tercom.domain.interactor.user.impl.DeletePetUseCaseImpl
import com.lanit_tercom.domain.interactor.user.impl.GetUserDetailsUseCaseImpl
import com.lanit_tercom.domain.repository.UserRepository
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl
import kotlinx.android.synthetic.main.fragment_user_detail.*
import kotlin.math.abs

/**
 * Фрагмент отображающий окно пользователя
 * @author nikolaygorokhov1@gmail.com
 * @author prostak.sasha111@mail.ru
 */
//Для профиля другого пользователя нужен другой экран UserDetails
class UserDetailFragment(private val userId: String?) : BaseFragment(), UserDetailView{
    private lateinit var petList: RecyclerView
    private lateinit var plansText: TextView
    private lateinit var aboutText: TextView
    private lateinit var petListAdapter: PetListAdapter
    private var pets: ArrayList<PetListItem> = ArrayList()
    private var userDetailPresenter: UserDetailPresenter? = null
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
        val deletePetUseCase: DeletePetUseCase = DeletePetUseCaseImpl(userRepository,
                threadExecutor, postExecutionThread)

        userDetailPresenter = UserDetailPresenter(getUserDetailsUseCase, deletePetUseCase)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_user_detail, container, false)

        //Инициализация списка питомцев
        petList = view.findViewById(R.id.pet_list)
        petList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        petListAdapter = PetListAdapter(pets)
        petList.adapter = petListAdapter

        //Это чтобы кнопка удаления в RecyclerView выезжала
        val swipeHelper: SwipeHelper = object : SwipeHelper(context, petList) {
            override fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder?, underlayButtons: ArrayList<UnderlayButton?>) {
                underlayButtons.add(UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3C30"),
                        UnderlayButtonClickListener {
                            if (viewHolder != null) {
                            userDetailPresenter?.deletePet(pets[viewHolder.adapterPosition].id)

                                pets.removeAt(viewHolder.adapterPosition)
                            }
                            petListAdapter.notifyDataSetChanged()
                        }
                ))
            }
        }

        //Динамическое задание высоты блока
        val appbar = view.findViewById<View>(R.id.appbar) as AppBarLayout
        //val bottomNav = view.findViewById<View>(R.id.bottom_nav) as BottomNavigationView
        val bottomNav = (activity as BaseActivity).findViewById<View>(R.id.nav_view) as BottomNavigationView

        val heightDp = resources.displayMetrics.heightPixels * 0.5 - 10 * resources.displayMetrics.density
        val lp = appbar.layoutParams as CoordinatorLayout.LayoutParams
        lp.height = heightDp.toInt()

        //Присвоение OnClickListener кнопкам
        view.findViewById<View>(R.id.edit_button).setOnClickListener { toUserEdit()
            bottomNav.visibility = View.GONE}
        view.findViewById<View>(R.id.add_pet_button).setOnClickListener { addPet()
            bottomNav.visibility = View.GONE}
        //view.findViewById<View>(R.id.to_map_button).setOnClickListener { toMap() }
        //view.findViewById<View>(R.id.to_chats_button).setOnClickListener { toChats() }
        //view.findViewById<View>(R.id.to_settings_button).setOnClickListener { toSettings() }

        //Присвоение OnClickListener текстовым полям "о себе" и "планы на прогулку" - так будет открываться фрагмент для редактирования соотв полей
        plansText = view.findViewById(R.id.plans_text)
        plansText.setOnClickListener {
            //(activity as BaseActivity).replaceFragment(R.id.ft_container, EditTextFragment("plans", user))
            (activity as BaseActivity).replaceFragment(R.id.nav_host_fragment, EditTextFragment("plans", user))

        }

        aboutText = view.findViewById(R.id.about_text)
        aboutText.setOnClickListener {
            //(activity as BaseActivity).replaceFragment(R.id.ft_container, EditTextFragment("about", user))
            (activity as BaseActivity).replaceFragment(R.id.nav_host_fragment, EditTextFragment("about", user))
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
        //(activity as BaseActivity).replaceFragment(R.id.ft_container, UserDetailEditFragment(user))
        (activity as BaseActivity).replaceFragment(R.id.nav_host_fragment, UserDetailEditFragment(user))

    }

    private fun addPet() {

        //(activity as BaseActivity).replaceFragment(R.id.ft_container, PetDetailEditFragment(userId))
        (activity as BaseActivity).replaceFragment(R.id.nav_host_fragment, PetDetailEditFragment(userId))
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

    /**
     * РЕНДЕРИТ ЮЗЕРА КАЖДЫЙ РАЗ ПРИ ОТКРЫТИИ ФРАГМЕНТА
     * ИНОГДА ВЫЛЕТАЕТ ПО ПОКА НЕ ЯСНЫМ ПРИЧИНАМ
     */
    override fun renderCurrentUser(user: UserModel?) {
        this.user = user
        val avatar = view?.findViewById<ImageView>(R.id.user_avatar)
        val name = view?.findViewById<TextView>(R.id.name)
        val age = view?.findViewById<TextView>(R.id.age)
        val plansText = view?.findViewById<TextView>(R.id.plans_text)
        val aboutText = view?.findViewById<TextView>(R.id.about_text)

        if (avatar != null) {
            Glide.with(this)
                    .load(user?.avatar)
                    .circleCrop()
                    .into(avatar)
        }


        name?.text = user?.name
        age?.text = ageDesc(user?.age)
        plansText?.text = user?.plans
        aboutText?.text = user?.about

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

    //Обратная связь с другими Activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Информация о прогулке из EditTextActivity
        if(requestCode == 2){
            if(resultCode == Activity.RESULT_OK){
                plansText.text = data?.getStringExtra("output")
                user?.plans = data?.getStringExtra("output")
            }
        }
        //Информация о себе из EditTextActivity
        if(requestCode == 3){
            if(resultCode == Activity.RESULT_OK){
                aboutText.text = data?.getStringExtra("output")
                user?.about = data?.getStringExtra("output")
            }
        }
        //Информация получаемая в процессе создания профиля собаки (для создания ссылки на этот профиль)
        if (requestCode == 4) {
            if (resultCode == Activity.RESULT_OK) {
                val pet = PetModel()

                pet.name = data?.getStringExtra("name")
                pet.age = data?.getStringExtra("age")?.toInt()
                pet.breed = data?.getStringExtra("breed")
                pet.gender = data?.getStringExtra("gender")
                pet.avatar = Uri.parse(data?.getStringExtra("avatarUri"))
                pet.character = data?.getStringArrayListExtra("character")

                val photos = ArrayList<Uri>()
                val uriStrings = data?.getStringArrayListExtra("photo")
                if(uriStrings != null){
                    for(photo in uriStrings)
                        if(photo != "0")
                            photos.add(Uri.parse(photo))
                    pet.photos = photos
                }


                val avatarUriString: String? = data?.getStringExtra("avatarUri")
                val name: String = data?.getStringExtra("name") ?: "1"
                val breed: String = data?.getStringExtra("breed") ?: "2"
                val age: String = data?.getStringExtra("age") ?: "3"
                val desc = "$breed, $age лет"
                pets.add(PetListItem(null, avatarUriString, name, desc))
                petListAdapter.notifyDataSetChanged()
            }
        }
    }

    //Класс представляющий список питомцев юзера
    data class PetListItem(val id: String?, val uri: String?, val name: String?, val desc: String?)
}