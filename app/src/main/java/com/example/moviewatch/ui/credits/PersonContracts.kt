package com.example.moviewatch.ui.credits

import com.example.moviewatch.db.MoviesEntity
import com.example.moviewatch.response.*
import com.example.moviewatch.ui.base.BasePresenter
import com.example.moviewatch.ui.base.BaseView

interface PersonContracts {

    interface View : BaseView{

        fun loadPersonMovie(data: PersonListResponse)

        fun loadPlayedIn(data: PlayedInListResponse)

    }
    interface Presenter : BasePresenter {

        fun callPerson(id: Int)

        fun callPlayedIn(id: Int)

    }
}