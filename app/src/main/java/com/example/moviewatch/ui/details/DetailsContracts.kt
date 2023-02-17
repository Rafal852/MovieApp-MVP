package com.example.moviewatch.ui.details

import com.example.moviewatch.db.MoviesEntity
import com.example.moviewatch.response.*
import com.example.moviewatch.ui.base.BasePresenter
import com.example.moviewatch.ui.base.BaseView

interface DetailsContracts {
    interface View : BaseView {
        fun loadDetailsMovie(data : DetailsMovieResponse)
        fun loadCreditsMovie(data : CreditsLisResponse)
        fun loadVideosMovie(data : VideoListResponse)




        //db
        fun updateFavorite(isAdded: Boolean)

    }

    interface Presenter : BasePresenter {
        fun callDetailsMovie(id: Int)
        fun callCreditsMovie(id: Int)
        fun saveMovie(entity: MoviesEntity)
        fun deleteMovie(entity: MoviesEntity)
        fun checkFavorite(id: Int)

    }
}