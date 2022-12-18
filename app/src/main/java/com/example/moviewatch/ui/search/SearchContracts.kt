package com.example.moviewatch.ui.search

import com.example.moviewatch.response.PopularMoviesListResponse
import com.example.moviewatch.ui.base.BasePresenter
import com.example.moviewatch.ui.base.BaseView

interface SearchContracts {
    interface View : BaseView {
        fun loadSearchMoviesList(data : PopularMoviesListResponse)
    }

    interface Presenter : BasePresenter {
        fun callSearchMoviesList(page: Int,query: String)
    }
}