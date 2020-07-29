package com.lanit_tercom.dogfriendly_studproject.tests.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl
import com.lanit_tercom.dogfriendly_studproject.data.entity.MessageEntity
import com.lanit_tercom.dogfriendly_studproject.data.firebase.message.FirebaseMessageEntityStore
import com.lanit_tercom.dogfriendly_studproject.data.firebase.message.MessageEntityStore
import com.lanit_tercom.dogfriendly_studproject.data.geofire.UserGeoFire
import com.lanit_tercom.dogfriendly_studproject.ui.activity.UserSignInActivity
import com.lanit_tercom.domain.exception.ErrorBundle
import kotlinx.android.synthetic.main.activity_test.*
import java.lang.Exception
import java.sql.Timestamp


class TestActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_map_test)
//        button_post.setOnClickListener(this)
//        button_get.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {

        when (p0?.id) {
            R.id.button_post -> {
                val userName = editText_userName.text.toString()
                val body = editText_body.text.toString()
                val channelId = editText_channelId.text.toString()
                val message = MessageEntity(userName, body)
                message.channelId = channelId
                FirebaseMessageEntityStore(null).postMessage(message, object: MessageEntityStore.MessagePostCallback{
                    override fun onError(errorBundle: ErrorBundle?) {
                        Toast.makeText(this@TestActivity, errorBundle?.errorMessage, Toast.LENGTH_LONG).show()
                    }

                    override fun onMessagePosted() {
                        Toast.makeText(this@TestActivity, "Posted!", Toast.LENGTH_LONG).show()
                    }
                })
            }
            R.id.button_get -> {
                FirebaseMessageEntityStore(null).getMessages(editText_channelId.text.toString(), object: MessageEntityStore.MessagesDetailCallback{
                    override fun onError(errorBundle: ErrorBundle?) {
                        textView.text = errorBundle?.errorMessage
                    }

                    override fun onMessagesLoaded(messages: MutableList<MessageEntity>?) {
                        messages?.forEach {textView.append("timestamp: ${it.timestampForMapper}\n")}
                    }
                })
            }

        }
    }

}