package com.lanit_tercom.dogfriendly_studproject.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.entity.ChannelEntity
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.ChannelEntityStore
import com.lanit_tercom.dogfriendly_studproject.data.firebase.channel.FirebaseChannelEntityStore
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*

//TODO - now it tests Channel methods. addChannel and deleteChannel doesn't work
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
                FirebaseChannelEntityStore(null).getChannels("1234",object : ChannelEntityStore.ChannelsDetailCallback{

                    override fun onChannelsLoaded(channels: MutableList<ChannelEntity>?) {
                        channels!!.forEach {
                            Log.i("TEST_ACTIVITY", it.id)
                        }
                    }

                    override fun onError(exception: Exception?) {
                        TODO("Not yet implemented")
                    }

                })
            }
            R.id.button_add ->{
                val entity:ChannelEntity = ChannelEntity()
                entity.id = "1234"
                entity.lastMessage="1234"
                entity.lastMessageOwner="1234"
                entity.name="1234"
                entity.timestamp=1234L
                val users: MutableList<HashMap<String, String>> = ArrayList()
                val first = HashMap<String, String>()
                first["userId"] = "1234"
                val second = HashMap<String, String>()
                second["userId"] = "1234"
                users.add(first)
                users.add(second)
                entity.members = users

                FirebaseChannelEntityStore(null).addChannel(entity ,object : ChannelEntityStore.ChannelDetailCallback{

                    override fun onChannelEdited() {
                        Log.i("TEST_ACTIVITY", "1234 ADDED")
                    }


                    override fun onError(exception: Exception?){
                        Log.i("TEST_ACTIVITY", "ERROR ADDING 1234")
                    }

                })

            }
            R.id.button_delete ->{
                val entity:ChannelEntity = ChannelEntity()
                entity.id = "1234"
                entity.lastMessage="1234"
                entity.lastMessageOwner="1234"
                entity.name="1234"
                entity.timestamp=1234L
                val users: MutableList<HashMap<String, String>> = ArrayList()
                val first = HashMap<String, String>()
                first["userId"] = "1234"
                val second = HashMap<String, String>()
                second["userId"] = "1234"
                users.add(first)
                users.add(second)
                entity.members = users


                FirebaseChannelEntityStore(null).deleteChannel(entity ,object : ChannelEntityStore.ChannelDetailCallback{

                    override fun onChannelEdited() {
                        Log.i("TEST_ACTIVITY", "1234 DELETED")
                    }


                    override fun onError(exception: Exception?){
                        Log.i("TEST_ACTIVITY", "ERROR DELETING 1234")
                    }

                })

            }
        }

    }

}