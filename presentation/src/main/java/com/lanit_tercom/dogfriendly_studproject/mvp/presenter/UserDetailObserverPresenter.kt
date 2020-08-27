package com.lanit_tercom.dogfriendly_studproject.mvp.presenter


import android.util.Log
import com.lanit_tercom.dogfriendly_studproject.mapper.UserDtoModelMapper
import com.lanit_tercom.dogfriendly_studproject.mvp.view.UserDetailView
import com.lanit_tercom.domain.dto.ChannelDto
import com.lanit_tercom.domain.dto.UserDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.interactor.channel.AddChannelUseCase
import com.lanit_tercom.domain.interactor.user.GetUserDetailsUseCase
import java.util.*

/**
 * Просто грузит модель юзера из базы данных.
 */
class UserDetailObserverPresenter(private val getUserDetailsUseCase: GetUserDetailsUseCase?,
                                  private val addChannelUseCase: AddChannelUseCase?) : BasePresenter() {

    private var view: UserDetailView? = null
    private var userId: String? = null

    fun initialize(userId: String?) {
        this.userId = userId
        this.loadUserDetails()
    }

    fun setView(view: UserDetailView){ this.view = view }

    private fun loadUserDetails() =
            getUserDetailsUseCase?.execute(userId, userDetailsCallback)

    fun startChatWithCurrentUser(hostID: String?, viewingUserID: String?, viewingUserName: String? ){
        val timestamp = System.currentTimeMillis();
        val members = LinkedList<String>();
        members.add(viewingUserID.toString())
        members.add(hostID.toString())
        Log.d("PRESENTER", "host - $hostID    veiwing - $viewingUserID")
        val channelDto  =  ChannelDto("id будет присвоен при добавлении в БД",
            viewingUserName, "Пусто...", viewingUserID, timestamp, members)
        addChannelUseCase?.execute(channelDto, addChannelCallback)
    }

    private fun showUserDetailsInView(userDto: UserDto?) {
        val userDtoModelMapper = UserDtoModelMapper()
        val userModel = userDtoModelMapper.map2(userDto)
        (view as UserDetailView).renderCurrentUser(userModel)
    }

    private val addChannelCallback: AddChannelUseCase.Callback = object  : AddChannelUseCase.Callback{
        override fun onChannelAdded() {
            //Переход к чату
            Log.d("USER_OBSERVER","CHANNEL CREATED")
        }

        override fun onError(errorBundle: ErrorBundle?) {
            errorBundle?.exception?.printStackTrace()
        }

    }
    private val userDetailsCallback: GetUserDetailsUseCase.Callback = object : GetUserDetailsUseCase.Callback {

        override fun onUserDataLoaded(userDto: UserDto?) =
                showUserDetailsInView(userDto)

        override fun onError(errorBundle: ErrorBundle?) {}

    }

    override fun onDestroy() {
        //TODO("Not yet implemented")
    }

}