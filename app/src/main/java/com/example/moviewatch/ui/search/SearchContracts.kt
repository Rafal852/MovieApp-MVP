package com.example.moviewatch.ui.search

import com.example.moviewatch.response.GenresListResponse
import com.example.moviewatch.response.NowPlayingResponse
import com.example.moviewatch.response.PopularMoviesListResponse
import com.example.moviewatch.ui.base.BasePresenter
import com.example.moviewatch.ui.base.BaseView

interface SearchContracts {
    interface View : BaseView {
        fun loadSearchMoviesList(data : PopularMoviesListResponse)
        fun loadNowPlayingMoviesList(data : NowPlayingResponse)
        fun loadGenres(data : GenresListResponse)
    }

    interface Presenter : BasePresenter {
        fun callGenres()
        fun callSearchMoviesList(page: Int,query: String)
        fun callNowPlayingMovieList(page: Int)
    }
}