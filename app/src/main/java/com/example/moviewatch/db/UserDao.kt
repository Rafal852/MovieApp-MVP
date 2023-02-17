package com.example.moviewatch.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moviewatch.utils.Constants.USER_TABLE


//@Dao
//interface UserDao {
//    @Insert
//    fun insert(user: UserEntity)
//
//    @Query("SELECT * FROM $USER_TABLE WHERE username = :username AND password = :password")
//    fun getUser(username: String, password: String): UserEntity?
//}