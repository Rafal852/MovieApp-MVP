package com.example.moviewatch.repository

//import com.example.moviewatch.db.MoviesDao
import com.example.moviewatch.db.MoviesEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class FirebaseRepository @Inject constructor() {

    private val database = FirebaseDatabase.getInstance()
    private val favoritesRef = database.getReference("favorites")

    fun insertMovie(userId: String, entity: MoviesEntity): Completable {
        return Completable.create { emitter ->
            favoritesRef
                .child(userId)
                .child(entity.id.toString())
                .setValue(entity)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onComplete()
                    } else {
                        emitter.onError(task.exception ?: Throwable("Unknown error"))
                    }
                }
        }
    }

    fun deleteMovie(userId: String, movieId: Int): Completable {
        return Completable.create { emitter ->
            favoritesRef
                .child(userId)
                .child(movieId.toString())
                .removeValue()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onComplete()
                    } else {
                        emitter.onError(task.exception ?: Throwable("Unknown error"))
                    }
                }
        }
    }

    fun existMovie(userId: String, movieId: Int): Single<Boolean> {
        return Single.create { emitter ->
            favoritesRef
                .child(userId)
                .child(movieId.toString())
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        emitter.onSuccess(snapshot.exists())
                    }

                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }
                })
        }
    }

    fun getFavorites(userId: String): Single<List<MoviesEntity>> {
        return Single.create { emitter ->
            favoritesRef
                .child(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val moviesList = mutableListOf<MoviesEntity>()
                        for (movieSnapshot in snapshot.children) {
                            movieSnapshot.getValue(MoviesEntity::class.java)?.let { moviesList.add(it) }
                        }
                        emitter.onSuccess(moviesList)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }
                })
        }
    }
}