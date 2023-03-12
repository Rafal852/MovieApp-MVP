package com.example.moviewatch.db

import androidx.room.*
import com.example.moviewatch.utils.Constants.MOVIES_TABLE
import io.reactivex.Completable
import io.reactivex.Observable

//@Dao
//interface MoviesDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun  insertMovie(entity: MoviesEntity) : Completable
//
//    @Delete
//    fun deleteMovie(entity: MoviesEntity) : Completable
//
//    @Query("SELECT * From $MOVIES_TABLE")
//    fun getAllMovies() : Observable<MutableList<MoviesEntity>>
//
//    @Query("SELECT EXISTS (SELECT 1 FROM $MOVIES_TABLE WHERE id = :id)")
//    fun existMovie(id:Int) : Observable<Boolean>
//
//
//}