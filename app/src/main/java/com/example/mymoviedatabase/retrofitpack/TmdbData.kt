package com.example.retrofittest2

import android.os.Parcelable
import com.example.mymoviedatabase.room.HistoryEntity
import kotlinx.android.parcel.Parcelize
import java.util.*

data class Movies(
    val results: List<Result>
)

@Parcelize
data class Result(
    val id: Int,
    val adult : Boolean,
    val overview: String,
    val budget: Int,
    val genre_name: String?,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
) : Parcelable

fun convertHistoryEntityToMovie(entityList: List<HistoryEntity>): List<Result> {
    return entityList.map {
        com.example.retrofittest2.Result(it.id, it.adult, it.overview, it.budget, it.genre_name,
                                            it.poster_path, it.release_date, it.title, it.vote_average, it.vote_count)
    }
}

fun convertMovieToEntity(movie: Result): HistoryEntity {
    return HistoryEntity(movie.id, movie.adult, movie.overview, movie.budget, movie.genre_name,
            movie.poster_path, movie.release_date, movie.title, movie.vote_average, movie.vote_count)
}