package com.example.moviewatch.ui.credits

import android.util.Log
import com.example.moviewatch.db.MoviesEntity
import com.example.moviewatch.repository.ApiRepository
import com.example.moviewatch.response.PersonListResponse
import com.example.moviewatch.response.TopMoviesResponse
import com.example.moviewatch.ui.base.BasePresenter
import com.example.moviewatch.ui.base.BasePresenterImpl
import com.example.moviewatch.ui.base.BaseView
import com.example.moviewatch.utils.applyIoScheduler
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class PersonPresenter
@Inject constructor(
    private val repository: ApiRepository,
    val view: PersonContracts.View,
) : PersonContracts.Presenter, BasePresenterImpl(){

    override fun callPerson(id: Int) {
        disposable = repository
            .getPersonList(id)
            .applyIoScheduler()
            .subscribe { response ->
                when (response.code()) {
                    in 200..202 ->
                        response.body()?.let { itBody ->
                            Log.e("PersonPresenter", "itBody : $itBody")
                            view.loadPersonMovie(itBody)
                        }
                    in 300..399 -> {
                        Log.d("PersonPresenter", " Redirection messages : ${response.code()}")
                    }
                    in 400..499 -> {
                        Log.d("PersonPresenter", " Client error responses : ${response.code()}")
                    }
                    in 500..599 -> {
                        Log.d("PersonPresenter", " Server error responses : ${response.code()}")
                    }
                }
            }
    }

    override fun callPlayedIn(id: Int) {
        disposable = repository
            .getPlayedInList(id)
            .applyIoScheduler()
            .subscribe { response ->
                when (response.code()) {
                    in 200..202 ->
                        response.body()?.let { itBody ->
                            Log.e("PersonPresenter", "itBody : $itBody")
                            view.loadPlayedIn(itBody)
                        }
                    in 300..399 -> {
                        Log.d("PersonPresenter", " Redirection messages : ${response.code()}")
                    }
                    in 400..499 -> {
                        Log.d("PersonPresenter", " Client error responses : ${response.code()}")
                    }
                    in 500..599 -> {
                        Log.d("PersonPresenter", " Server error responses : ${response.code()}")
                    }
                }
            }
    }

}
