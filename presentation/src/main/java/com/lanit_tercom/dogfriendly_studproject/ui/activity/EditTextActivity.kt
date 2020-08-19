package com.lanit_tercom.dogfriendly_studproject.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailEditPresenter


class EditTextActivity : AppCompatActivity() {
    private lateinit var btnReady: Button
    private lateinit var btnBack: ImageButton
    private lateinit var titleText: TextView
    private lateinit var editText: EditText
    private var userDetailEditPresenter: UserDetailEditPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_edit_text)

        btnReady = findViewById(R.id.ready_button)
        btnBack = findViewById(R.id.back_button)
        editText = findViewById(R.id.editText)
        titleText = findViewById(R.id.title_text)
        titleText.text = intent.getStringExtra("title")
        when(intent.getStringExtra("editText")){
            "plans" -> {
                editText.filters = arrayOf<InputFilter>(LengthFilter(150))
                editText.hint="Напиши здесь о своих планах на ближайшую прогулку"
            }
            "about" -> {
                editText.filters = arrayOf<InputFilter>(LengthFilter(200))
                editText.hint="Расскажите о своем питомце"
            }
        }

        btnBack.setOnClickListener { finish() }

        btnReady.setOnClickListener {
            val data = Intent()
            data.putExtra("output", editText.text.toString())
            setResult(Activity.RESULT_OK, data)
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
            finish()
        }


    }






}