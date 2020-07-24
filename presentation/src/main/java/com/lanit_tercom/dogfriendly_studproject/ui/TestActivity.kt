package com.lanit_tercom.dogfriendly_studproject.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.entity.ChannelEntity
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStore
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.FirebaseChannelEntityStore
import com.lanit_tercom.dogfriendly_studproject.data.mapper.ChannelEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.ChannelRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.domain.dto.ChannelDto
import com.lanit_tercom.domain.exception.ErrorBundle
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.interactor.channel.AddChannelUseCase
import com.lanit_tercom.domain.interactor.channel.GetChannelsUseCase
import com.lanit_tercom.domain.interactor.channel.impl.AddChannelUseCaseImpl
import com.lanit_tercom.domain.interactor.channel.impl.GetChannelsUseCaseImpl
import com.lanit_tercom.domain.repository.ChannelRepository
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*


class TestActivity : AppCompatActivity(), View.OnClickListener {
    private val threadExecutor: ThreadExecutor = JobExecutor.getInstance()
    private val postExecutionThread: PostExecutionThread = UIThread.getInstance()

    private val networkManager: NetworkManager = NetworkManagerImpl(this)
    private val channelEntityStoreFactory = ChannelEntityStoreFactory(networkManager, null)
    private val channelEntityDtoMapper = ChannelEntityDtoMapper()
    private val channelRepository = ChannelRepositoryImpl(channelEntityStoreFactory, channelEntityDtoMapper)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        button_get.setOnClickListener(this)
        button_add.setOnClickListener(this)
        button_delete.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {



        when (p0?.id) {
            R.id.button_get ->{

                val getChannelsUseCase = GetChannelsUseCaseImpl(channelRepository, threadExecutor, postExecutionThread)

                val getChannelsCallback: GetChannelsUseCase.Callback = object : GetChannelsUseCase.Callback{

                    override fun onChannelsLoaded(channels: MutableList<ChannelDto>?) {
                        Log.i("TEST_ACTIVITY", channels?.size.toString())
                    }

                    override fun onError(errorBundle: ErrorBundle?) {
                        Log.i("TEST_ACTIVITY", "error")
                    }
                }

                getChannelsUseCase.execute("2345", getChannelsCallback);

            }
            R.id.button_add ->{
//                val entity:ChannelEntity = ChannelEntity()
//                entity.id = "1"
//                entity.lastMessage="1234"
//                entity.lastMessageOwner="1234"
//                entity.name="1234"
//                entity.timestamp=1234L
//                val users: MutableList<HashMap<String, String>> = ArrayList()
//                val first = HashMap<String, String>()
//                first["userId"] = "1234"
//                val second = HashMap<String, String>()
//                second["userId"] = "2345"
//                users.add(first)
//                users.add(second)
//                entity.members = users

                val entity2 = ChannelEntity()
                entity2.id = "2"
                entity2.lastMessage="2345"
                entity2.lastMessageOwner="2345"
                entity2.name="2345"
                entity2.timestamp=2345L
                val users2: MutableList<HashMap<String, String>> = ArrayList()
                val first2 = HashMap<String, String>()
                first2["userId"] = "2345"
                val second2 = HashMap<String, String>()
                second2["userId"] = "3456"
                users2.add(first2)
                users2.add(second2)
                entity2.members = users2



                val addChannelUserCase = AddChannelUseCaseImpl(channelRepository, threadExecutor, postExecutionThread)

                val addChannelCallback: AddChannelUseCase.Callback = object : AddChannelUseCase.Callback{

                    override fun onChannelAdded() {
                        Log.i("TEST_ACTIVITY", "added_successfully")
                    }

                    override fun onError(errorBundle: ErrorBundle?) {
                        Log.i("TEST_ACTIVITY", "error")
                    }
                }

                addChannelUserCase.execute(channelEntityDtoMapper.map2(entity2), addChannelCallback);

            }
            R.id.button_delete ->{

               //If get and add works, let's assume that delete works too


            }

        }
    }

}