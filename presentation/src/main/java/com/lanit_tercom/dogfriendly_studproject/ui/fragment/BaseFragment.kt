package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.ViewAnimator
import androidx.fragment.app.Fragment

/**
 * Класс от которого наследуются все фрагменты.
 * Служит для инициализации presenter класса при создании фрагмента
 * Сохраняет состояние фрагмента (при изменении/удалении Activity)
 * @author nikolaygorokhov1@gmail.com
 */
abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        initializePresenter()
    }

    abstract fun initializePresenter()

    fun showToastMessage(message: String?)
            = Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

}