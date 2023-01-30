package com.example.moviewatch.api

import com.example.moviewatch.response.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {






    @GET("movie/upcoming")
    fun getUpcomingMoviesList(@Query("page") page: Int): Single<Response<UpcomingMoviesListResponse>>

    @GET("movie/top_rated")
    fun getTopRatedMoviesList(@Query("page") page: Int): Single<Response<TopMoviesResponse>>

    @GET("genre/movie/list")
    fun getGenres(): Single<Response<GenresListResponse>>

    @GET("discover/movie")
    fun getMoviesGenres(@Query("page") page: Int,@Query("with_genres") with_genres: String): Single<Response<PopularMoviesListResponse>>

    @GET("movie/popular")
    fun getPopularMoviesList(@Query("page") page: Int ): Single<Response<PopularMoviesListResponse>>

    @GET("search/movie")
    fun getSearchMoviesList(@Query("page") page: Int,@Query("query") query: String): Single<Response<PopularMoviesListResponse>>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<Response<DetailsMovieResponse>>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(@Path("movie_id") id: Int): Single<Response<CreditsLisResponse>>

    @GET("movie/{movie_id}/videos")
    fun getMovieVideos(@Path("movie_id") id: Int): Single<Response<VideoListResponse >>


}