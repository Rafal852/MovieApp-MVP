package com.example.moviewatch.ui.genres

import com.example.moviewatch.response.PopularMoviesListResponse
import com.example.moviewatch.ui.base.BasePresenter
import com.example.moviewatch.ui.base.BaseView

interface GenresContracts {
    interface View : BaseView{
        fun loadMoviesGenres(data : PopularMoviesListResponse)

    }

    interface Presenter : BasePresenter{
        fun callMoviesGenres(page: Int,with_genres: String)
    }
}