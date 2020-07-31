package com.lanit_tercom.dogfriendly_studproject.tests.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lanit_tercom.dogfriendly_studproject.R


/**
 * Закоменченный код - нерабочая версия с CollapsingToolBar
 */
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_detail_test)

        val appbar = findViewById<View>(R.id.appbar) as AppBarLayout
        val bottomNav = findViewById<View>(R.id.bottom_nav) as BottomNavigationView
        val heightDp = resources.displayMetrics.heightPixels  * 0.5 - (48+10) * resources.displayMetrics.density
        val lp = appbar.layoutParams as CoordinatorLayout.LayoutParams
        lp.height = heightDp.toInt()

        appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener(){

            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {

                if(state == State.EXPANDED)
                    bottomNav.visibility = View.GONE
                    //Ну я и не сомневался почему-то что это не прокатит. Но в самом деле это фича - юзер не нажмет случайно на кнопку "домой"
                    lp.height = (resources.displayMetrics.heightPixels  * 0.5 * resources.displayMetrics.density).toInt()
                if(state == State.COLLAPSED)
                    bottomNav.visibility = View.VISIBLE
            }

        })
    }

    abstract class AppBarStateChangeListener : OnOffsetChangedListener {
        enum class State {
            EXPANDED, COLLAPSED, IDLE
        }

        private var mCurrentState = State.IDLE
        override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
            mCurrentState = if (i == 0) {
                if (mCurrentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED)
                }
                State.EXPANDED
            } else if (Math.abs(i) >= appBarLayout.totalScrollRange) {
                if (mCurrentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED)
                }
                State.COLLAPSED
            } else {
                if (mCurrentState != State.IDLE) {
                    onStateChanged(appBarLayout, State.IDLE)
                }
                State.IDLE
            }
        }

        abstract fun onStateChanged(appBarLayout: AppBarLayout?, state: State?)
    }
}



