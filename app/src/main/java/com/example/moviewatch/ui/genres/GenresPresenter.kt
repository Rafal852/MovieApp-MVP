package com.example.moviewatch.ui.genres

import android.util.Log
import com.example.moviewatch.repository.ApiRepository
import com.example.moviewatch.ui.base.BasePresenterImpl
import com.example.moviewatch.utils.applyIoScheduler
import javax.inject.Inject

class GenresPresenter @Inject constructor(
    private val repository: ApiRepository,
    val view: GenresContracts.View
) : GenresContracts.Presenter, BasePresenterImpl(){

    override fun callMoviesGenres(page: Int, with_genres: String) {
        disposable = repository
            .getMoviesGenres(page, with_genres)
            .applyIoScheduler()
            .subscribe { response ->
                when (response.code()) {
                    in 200..202 ->
                        response.body()?.let { itBody ->
                            Log.e("MainPresenter", "itBody : $itBody")
                            view.loadMoviesGenres(itBody)
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