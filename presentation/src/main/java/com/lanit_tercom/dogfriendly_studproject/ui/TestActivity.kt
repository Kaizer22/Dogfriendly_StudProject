package com.lanit_tercom.dogfriendly_studproject.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.data.entity.MessageEntity
import com.lanit_tercom.dogfriendly_studproject.data.firebase.message.FirebaseMessageEntityStore
import com.lanit_tercom.dogfriendly_studproject.data.firebase.message.MessageEntityStore
import com.lanit_tercom.domain.exception.ErrorBundle
import kotlinx.android.synthetic.main.activity_test.*
import java.sql.Timestamp

class TestActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        button_edit.setOnClickListener(this)
        button_post.setOnClickListener(this)
        button_delete.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val id = editText_id.text.toString()
        val channelId = editText_channelId.text.toString()
        val userName = editText_userName.text.toString()
        val body = editText_body.text.toString()
        val message = MessageEntity(userName, body)
        message.id = id
        message.channelId = channelId
        message.timestamp = 10000//Timestamp(10000)

        when (p0?.id) {
            R.id.button_post ->{
                FirebaseMessageEntityStore(null).postMessage(message, object : MessageEntityStore.MessagePostCallback{
                    override fun onMessagePosted() {
                        Toast.makeText(this@TestActivity, "Posted!", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(errorBundle: ErrorBundle?) {
                        Toast.makeText(this@TestActivity, "Error (post)!", Toast.LENGTH_LONG).show()
                    }
                })
            }
            R.id.button_edit ->{
                FirebaseMessageEntityStore(null).editMessage(message, object : MessageEntityStore.MessageEditCallback{
                    override fun onMessageEdited() {
                        Toast.makeText(this@TestActivity, "Edited!", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(errorBundle: ErrorBundle?) {
                        Toast.makeText(this@TestActivity, "Error (edit)!", Toast.LENGTH_LONG).show()
                    }
                })

            }
            R.id.button_delete ->{
                FirebaseMessageEntityStore(null).deleteMessage(message, object : MessageEntityStore.MessageDeleteCallback{
                    override fun onMessageDeleted() {
                        Toast.makeText(this@TestActivity, "Deleted!", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(errorBundle: ErrorBundle?) {
                        Toast.makeText(this@TestActivity, "Error (delete)!", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

}