package com.lanit_tercom.dogfriendly_studproject.mvp.view

import com.lanit_tercom.dogfriendly_studproject.ui.fragment.TestSignUpFragment

interface TestSignUpView: LoadDataView {
    fun changeSignUpStage(stage: TestSignUpFragment.SignUpStage)
}