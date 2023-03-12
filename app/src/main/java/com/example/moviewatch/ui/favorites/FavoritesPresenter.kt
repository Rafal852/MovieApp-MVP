package com.example.moviewatch.ui.favorites

import com.example.moviewatch.db.MoviesEntity

import com.example.moviewatch.repository.FirebaseRepository
import com.example.moviewatch.ui.base.BasePresenterImpl
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class FavoritesPresenter
@Inject constructor(

    private val firebaseRepository: FirebaseRepository,
    val view: FavoritesContracts.View,
) : FavoritesContracts.Presenter, BasePresenterImpl() {

    companion object{
        const val TAG="FavoritesPresenter"
        const val REQUEST_CAMERA = 100
    }

    override fun callFavoritesList() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        disposable = firebaseRepository
            .getFavorites(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                if (it.isNotEmpty()) {
                    view.loadFavoriteMovieList(it as MutableList<MoviesEntity>)
                } else {
                    view.showEmpty()
                }
            }, { error ->
                // Handle error
            })
    }

}