package com.lanit_tercom.dogfriendly_studproject.mvp.view

/**
 * Базовый интерфейс для отображений
 * @author nikolaygorokhov1@gmail.com
 */
interface LoadDataView {

    fun showLoading()

    fun hideLoading()

    fun showError(message: String)

}