package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import android.widget.Toast
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

    protected abstract fun initializePresenter()
    protected fun showToastMessage(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}