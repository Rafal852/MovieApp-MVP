package com.example.moviewatch.ui.favorites

import com.example.moviewatch.db.MoviesEntity
import com.example.moviewatch.ui.base.BasePresenter
import com.example.moviewatch.ui.base.BaseView

interface  FavoritesContracts {

    interface View : BaseView {
        fun loadFavoriteMovieList(data: MutableList<MoviesEntity>)
        fun showEmpty()
    }

    interface Presenter : BasePresenter {
        fun callFavoritesList()
    }

}