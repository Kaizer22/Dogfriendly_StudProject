package com.lanit_tercom.dogfriendly_studproject.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lanit_tercom.dogfriendly_studproject.R
import com.lanit_tercom.dogfriendly_studproject.mvp.presenter.UserDetailPresenter


class MapFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun initializePresenter() {
        //TODO something
    }

}