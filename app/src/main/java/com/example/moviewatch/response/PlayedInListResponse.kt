package com.example.moviewatch.response

data class PlayedInListResponse(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
){
    data class Cast(
        val adult: Boolean,
        val backdrop_path: String,
        val character: String,
        val credit_id: String,
        val genre_ids: List<Int>,
        val id: Int,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: Double,
        val poster_path: String,
        val release_date: String,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: Int
    )

    data class Crew(
        val adult: Boolean,
        val backdrop_path: String,
        val credit_id: String,
        val department: String,
        val genre_ids: List<Int>,
        val id: Int,
        val job: String,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: Double,
        val poster_path: String,
        val release_date: String,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: Int
    )
}