package com.lanit_tercom.dogfriendly_studproject.tests.ui

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.lanit_tercom.dogfriendly_studproject.R


/**
 * Закоменченный код - нерабочая версия с CollapsingToolBar
 */
class TestActivity : AppCompatActivity() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.user_detail_test)
//    }

    var expandableView: LinearLayout? = null
    var openDescButton: ImageButton? = null
    var cardView: CardView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_detail_test2)
        expandableView = findViewById(R.id.expandable_desc)
        cardView = findViewById(R.id.cardView)
        openDescButton = findViewById(R.id.open_desc_button)

        openDescButton?.setOnClickListener(View.OnClickListener {
            if (expandableView?.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                expandableView?.visibility = View.VISIBLE

            } else {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                expandableView?.visibility = View.GONE
            }
        })
    }
}