package com.lanit_tercom.dogfriendly_studproject.mvp.presenter

import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity
import com.lanit_tercom.dogfriendly_studproject.data.firebase.UserEntityStore
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.model.UserModel
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailsView
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.get.GetUserDetailsUseCase
import java.lang.Exception

/**
 * presenter класс для работы с конкретным пользователем
 * @author prostak.sasha111@mail.ru
 * @author nikolaygorokhov1@gmail.com
 */
class UserDetailPresenter(private val useCaseTemp: UseCaseTemp?,
                          private val entityStore: UserEntityStore? ,
                          private val getUserDetailsUseCase: GetUserDetailsUseCase?) : BasePresenter() {

    private var userId: String? = null

    fun initialize(userId: String?) {
        this.userId = userId
        this.loadUserDetails()
    }

    fun setView(view: UserDetailsView){
        this.view = view
    }

    private fun loadUserDetails() {
        entityStore?.getUserById(userId, userByIdCallback)
        //getUserDetailsUseCase!!.execute(userId, userDetailsCallback)
    }

    private fun showUserDetailsInView(userDto: UserEntity?) {
        val userEntityModelMapper: UserEntityDtoMapper = UserEntityDtoMapper()
        val userDtoModelMapper: UserDtoModelMapper = UserDtoModelMapper()
        val userDto = userEntityModelMapper.map2(userDto)
        val userModel = userDtoModelMapper.map2(userDto)
        (view as UserDetailsView).renderCurrentUser(userModel)
    }

    /*
    Короче, я так понял у нас в презентере должен быть этот callback, засчет которого мы сможем
    вызывать getUserById из entityStore. Тоже самое с getAllUsers. Если я правильно понял то хорошо,
    если нет то я дебил))))0

    Хотя мы должны же через интерфейс в domain работать, значит через GetUserDetailsUseCase.Callback
    Короче я опять ничего не понял...
     */
    private val userByIdCallback: UserEntityStore.UserByIdCallback = object : UserEntityStore.UserByIdCallback{
        override fun onUserLoaded(user: UserEntity?) {
            this@UserDetailPresenter.showUserDetailsInView(user)
        }

        override fun onError(exception: Exception?) {
            TODO("Not yet implemented")
        }
    }

    private val userListCallback: UserEntityStore.UserListCallback = object: UserEntityStore.UserListCallback{
        override fun onUsersListLoaded(users: MutableList<UserEntity>?) {
            TODO("Not yet implemented")
        }

        override fun onError(exception: Exception?) {
            TODO("Not yet implemented")
        }

    }

      //Скорее всего надо использовать это, но я все равно ничего не понимаю, мб у тебя прокатит
//    private val userDetailsCallback: GetUserDetailsUseCase.Callback = object : GetUserDetailsUseCase.Callback {
//        override fun onUserDataLoaded(userDto: UserDto?) {
//            this@UserDetailPresenter.showUserDetailsInView(userDto)
//
//        }
//
//        override fun onError(errorBundle: ErrorBundle?) {
//
//        }
//    }

    //fun renderUser(user: UserModel?) = (view as UserDetailsView).renderCurrentUser(user)

}