package com.example.moviewatch.ui.base

interface BaseView {
    fun showLoading()
    fun hideLoading()
//    fun isNetworkAvailable() : Boolean
//    fun showNetworkError()
//    fun showEmpty()
    fun responseError(error: String)
}