package com.example.moviewatch.ui.details

import android.util.Log
import com.example.moviewatch.db.MoviesEntity
import com.example.moviewatch.repository.ApiRepository

import com.example.moviewatch.repository.FirebaseRepository
import com.example.moviewatch.ui.base.BasePresenterImpl
import com.example.moviewatch.ui.favorites.FavoritesPresenter.Companion.TAG
import com.example.moviewatch.utils.applyIoScheduler
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsPresenter
@Inject constructor(
    private val repository: ApiRepository,
    private val firebaseRepository: FirebaseRepository,
    val view: DetailsContracts.View,
) : DetailsContracts.Presenter, BasePresenterImpl() {


    override fun callDetailsMovie(id: Int) {
        disposable = repository
            .getMovieDetails(id)
            .applyIoScheduler()
            .subscribe { response ->
                when (response.code()) {
                    in 200..202 ->
                        response.body()?.let { itBody ->
                            Log.e("DetailsPresenter", "itBody : $itBody")
                            view.loadDetailsMovie(itBody)
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

    override fun callCreditsMovie(id: Int) {
        disposable = repository
            .getMovieCredits(id)
            .applyIoScheduler()
            .subscribe { response ->
                when (response.code()) {
                    in 200..202 ->
                        response.body()?.let { itBody ->
                            Log.e("DetailsPresenter", "itBody : $itBody")
                            view.loadCreditsMovie(itBody)
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



    fun callVideoMovie(id: Int) {
        disposable = repository
            .getVideoMoviesList(id)
            .applyIoScheduler()
            .subscribe { response ->
                when (response.code()) {
                    in 200..202 ->
                        response.body()?.let { itBody ->
                            Log.e("DetailsPresenter", "itBody : $itBody")
                            view.loadVideosMovie(itBody)
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

    override fun saveMovie(entity: MoviesEntity) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        disposable = firebaseRepository.insertMovie(userId, entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.updateFavorite(true)
            }
    }

    override fun deleteMovie(entity: MoviesEntity) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        disposable = firebaseRepository.deleteMovie(userId, entity.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.updateFavorite(false)
            }
    }

    override fun checkFavorite(id: Int) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        disposable = firebaseRepository.existMovie(userId, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { it -> view.updateFavorite(it) }, // specify the type of the lambda expression
                { error -> Log.e(   TAG, "Error checking favorite", error) }
            )
    }





//    override fun saveMovie(entity: MoviesEntity) {
//        disposable = dbRepository.insertMovie(entity)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                view.updateFavorite(true)
//            }
//    }
//
//
//    override fun deleteMovie(entity: MoviesEntity) {
//        disposable = dbRepository.deleteMovie(entity)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                view.updateFavorite(false)
//            }
//    }
//
//    override fun checkFavorite(id: Int) {
//        disposable = dbRepository.existMovie(id)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                view.updateFavorite(it)
//            }
    }



