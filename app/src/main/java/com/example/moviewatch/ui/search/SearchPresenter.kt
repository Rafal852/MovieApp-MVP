package com.example.moviewatch.ui.search

import android.util.Log
import com.example.moviewatch.repository.ApiRepository
import com.example.moviewatch.ui.base.BasePresenterImpl
import com.example.moviewatch.utils.applyIoScheduler
import javax.inject.Inject

class SearchPresenter
@Inject constructor(
    private val repository: ApiRepository,
    val view: SearchContracts.View,
) : SearchContracts.Presenter, BasePresenterImpl() {


    override fun callSearchMoviesList(page: Int,query: String) {
        disposable = repository
            .getSearchMoviesList(1, query)
            .applyIoScheduler()
            .subscribe { response ->
                when (response.code()) {
                    in 200..202 ->
                        response.body()?.let { itBody ->
                            Log.e("DetailsPresenter", "itBody : $itBody")
                            view.loadSearchMoviesList(itBody)
                        }
                    in 300..399 -> {
                        Log.d("DetailsPresenter", " Redirection messages : ${response.code()}")
                    }
                    in 400..499 -> {
                        Log.d("DetailsPresenter", " Client error responses : ${response.code()}")
                    }
                    in 500..599 -> {
                        Log.d("DetailsPresenter", " Server error responses : ${response.code()}")
                    }
                }
            }
    }

    override fun callNowPlayingMovieList(page: Int) {
        disposable = repository
            .getNowPlayingMoviesList(page)
            .applyIoScheduler()
            .subscribe { response ->
                when (response.code()) {
                    in 200..202 ->
                        response.body()?.let { itBody ->
                            Log.e("MainPresenter", "itBody : $itBody")
                            view.loadNowPlayingMoviesList(itBody)
                        }
                    in 300..399 -> {
                        Log.d("MainPresenter", " Redirection messages : ${response.code()}")
                    }
                    in 400..499 -> {
                        Log.d("MainPresenter", " Client error responses : ${response.code()}")
                    }
                    in 500..599 -> {
                        Log.d("MainPresenter", " Server error responses : ${response.code()}")
                    }

                }

            }
    }

    override fun callGenres() {
        disposable = repository
            .getGenres()
            .applyIoScheduler()
            .subscribe { response ->
                when (response.code()) {
                    in 200..202 ->
                        response.body()?.let { itBody ->
                            Log.e("MainPresenter", "itBody : $itBody")
                            view.loadGenres(itBody)
                        }
                    in 300..399 -> {
                        Log.d("MainPresenter", " Redirection messages : ${response.code()}")
                    }
                    in 400..499 -> {
                        Log.d("MainPresenter", " Client error responses : ${response.code()}")
                    }
                    in 500..599 -> {
                        Log.d("MainPresenter", " Server error responses : ${response.code()}")
                    }
                }
            }
    }


}