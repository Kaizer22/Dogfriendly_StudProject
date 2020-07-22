package com.lanit_tercom.dogfriendly_studproject.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.entity.ChannelEntity
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStore
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.FirebaseChannelEntityStore
import com.lanit_tercom.domain.exception.ErrorBundle
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*


class TestActivity : AppCompatActivity(), View.OnClickListener {
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
                FirebaseChannelEntityStore(null).getChannels("3456",object : ChannelEntityStore.GetChannelsCallback {


                    override fun onChannelsLoaded(channels: MutableList<ChannelEntity>?) {
                        channels!!.forEach {
                            Log.i("TEST_ACTIVITY", it.id)
                        }
                    }

                    override fun onError(errorBundle: ErrorBundle?) {
                        Log.i("TEST_ACTIVITY", "error while getting channels")
                    }

                })
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

                FirebaseChannelEntityStore(null).addChannel(entity2 ,object : ChannelEntityStore.AddChannelCallback {


                    override fun onChannelAdded() {
                        Log.i("TEST_ACTIVITY", "added successfully")
                    }

                    override fun onError(errorBundle: ErrorBundle?) {
                        Log.i("TEST_ACTIVITY", "error while adding channels")
                    }

                })

            }
            R.id.button_delete ->{

                FirebaseChannelEntityStore(null).getChannels("2345",object : ChannelEntityStore.GetChannelsCallback {


                    override fun onChannelsLoaded(channels: MutableList<ChannelEntity>?) {
                        val entity = channels?.get(0)

                        FirebaseChannelEntityStore(null).deleteChannel("2345" ,entity ,object : ChannelEntityStore.DeleteChannelCallback {

                            override fun onChannelDeleted() {
                                Log.i("TEST_ACTIVITY", "deleted successfully")
                            }

                            override fun onError(errorBundle: ErrorBundle?) {
                                Log.i("TEST_ACTIVITY", "error while deleting channel")
                            }

                        })
                    }

                    override fun onError(errorBundle: ErrorBundle?) {
                        Log.i("TEST_ACTIVITY", "error while getting channels")
                    }


                })
            }

        }
    }

}