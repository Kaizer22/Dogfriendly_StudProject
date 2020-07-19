package com.lanit_tercom.dogfriendly_studproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor
import com.lanit_tercom.dogfriendly_studproject.data.firebase.FirebaseMessageEntityStore
import com.lanit_tercom.dogfriendly_studproject.data.firebase.MessageEntityStore
import com.lanit_tercom.dogfriendly_studproject.data.firebase.UserEntityStoreFactory
import com.lanit_tercom.dogfriendly_studproject.data.mapper.UserEntityDtoMapper
import com.lanit_tercom.dogfriendly_studproject.data.repository.UserRepositoryImpl
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread
import com.lanit_tercom.domain.executor.PostExecutionThread
import com.lanit_tercom.domain.executor.ThreadExecutor
import com.lanit_tercom.domain.repository.UserRepository
import com.lanit_tercom.library.data.manager.NetworkManager
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl
import kotlinx.android.synthetic.main.activity_test.*
import java.lang.Exception
import java.sql.Timestamp

class TestActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        button_send.setOnClickListener(this)
        button_update.setOnClickListener(this)
        button_delete.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val id = editText_id.text.toString()
        val name = editText_username.text.toString()
        val body = editText_body.text.toString()
        when (p0?.id) {
//            R.id.button_send ->{
//                FirebaseMessageEntityStore().createMessage(id, Timestamp(1000), name, body, object : MessageEntityStore.MessageCreateCallback{
//                    override fun onMessageCreated() {
//                        Toast.makeText(this@TestActivity, "Creating succsessfuly!", Toast.LENGTH_LONG).show()
//                    }
//
//                    override fun onError(exception: Exception?) {
//                        TODO("Not yet implemented")
//                    }
//                })
//            }
//            R.id.button_update->{
//                FirebaseMessageEntityStore().updateMessage(id, body, object: MessageEntityStore.MessageUpdateCallback{
//                    override fun onMessageUpdated() {
//                        Toast.makeText(this@TestActivity, "Updating succsessfuly!", Toast.LENGTH_LONG).show()
//                    }
//
//                    override fun onError(exception: Exception?) {
//                        TODO("Not yet implemented")
//                    }
//                })
//            }
//            R.id.button_delete->{
//                FirebaseMessageEntityStore().deleteMessage(id, object: MessageEntityStore.MessageDeleteCallback{
//                    override fun onMessageDeleteCallback() {
//                        Toast.makeText(this@TestActivity, "Deleting succsessfuly!", Toast.LENGTH_LONG).show()
//                    }
//
//                    override fun onError(exception: Exception?) {
//                        TODO("Not yet implemented")
//                    }
//                })
//            }
//        }
        }
    }
}