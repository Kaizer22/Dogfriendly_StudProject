package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        initializePresenter()
    }

    protected abstract fun initializePresenter()
    protected fun showToastMessage(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}