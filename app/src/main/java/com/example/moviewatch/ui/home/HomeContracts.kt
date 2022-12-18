package com.example.moviewatch.ui.home

import com.example.moviewatch.response.PopularMoviesListResponse
import com.example.moviewatch.response.GenresListResponse
import com.example.moviewatch.response.TopMoviesResponse
import com.example.moviewatch.response.UpcomingMoviesListResponse
import com.example.moviewatch.ui.base.BasePresenter
import com.example.moviewatch.ui.base.BaseView

interface  HomeContracts {
    interface View : BaseView {
        fun loadUpcomingMoviesList(data : UpcomingMoviesListResponse)
        fun loadGenres(data : GenresListResponse)
        fun loadMoviesGenres(data : PopularMoviesListResponse)
        fun loadPopularMoviesList(data : PopularMoviesListResponse)
        fun loadTopRatedMoviesList(data: TopMoviesResponse)
    }

    interface Presenter : BasePresenter {
        fun callUpcomingMoviesList(page: Int)
        fun callGenres()
        fun callMoviesGenres(page: Int,with_genres: String)
        fun callPopularMoviesList(page: Int)
    }
}