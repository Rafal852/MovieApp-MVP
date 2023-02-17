package com.example.moviewatch.repository

import com.example.moviewatch.api.ApiServices
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiServices: ApiServices
) {
    //Api
    fun getUpcomingMoviesList(page: Int) = apiServices.getUpcomingMoviesList(page)
    fun getGenres()=apiServices.getGenres()
    fun getMoviesGenres(page: Int ,with_genres :String)=apiServices.getMoviesGenres(page,with_genres)
    fun getPopularMoviesList(page: Int) = apiServices.getPopularMoviesList(page)
    fun getMovieDetails(id: Int) = apiServices.getMovieDetails(id)



    fun getSearchMoviesList(page: Int,query: String) = apiServices.getSearchMoviesList(page,query)
    fun getMovieCredits(id: Int) = apiServices.getMovieCredits(id)

    fun getTopRatedMoviesList(page: Int) = apiServices.getTopRatedMoviesList(page)

    fun getNowPlayingMoviesList(page: Int) = apiServices.getNowPlayingMovies(page)

    fun getVideoMoviesList(id: Int) = apiServices.getMovieVideos(id)

    fun getPersonList(id: Int) = apiServices.getPersonDetails(id)

    fun getPlayedInList(id: Int) = apiServices.getPlayedIn(id)

}