package com.example.tmdbdata

import android.os.Parcelable
import com.example.mymoviedatabase.model.Movie
import com.example.mymoviedatabase.room.HistoryEntity
import kotlinx.android.parcel.Parcelize
import java.util.*

data class Movies(
    val results: List<Result>
)

data class Artists(
        val results: List<ArtistResult>
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

@Parcelize
data class ArtistResult(
        val id: Int,
        val adult : Boolean,
        val biography: String,
        val birthday: String?,
        val name: String,
        val place_of_birth: String?,
        val vote_count: Int,
        val popularity: Double,
        val poster_path: String?
) : Parcelable

@Parcelize
data class ArtistTest(
        val id: Int,
        val adult : Boolean,
        val biography: String,
        val birthday: String?,
        val name: String,
        val place_of_birth: String?,
        val popularity: Double,
) : Parcelable

fun getDefaultArtist() = ArtistTest(123, true, "comedy", "222",
        "Mark", "Omsk", 1.0)

fun convertHistoryEntityToMovie(entityList: List<HistoryEntity>): List<Result> {
    return entityList.map {
        com.example.tmdbdata.Result(it.id, it.adult, it.overview, it.budget, it.genre_name,
                                            it.poster_path, it.release_date, it.title, it.vote_average, it.vote_count)
    }
}

fun convertMovieToEntity(movie: Result): HistoryEntity {
    return HistoryEntity(movie.id, movie.adult, movie.overview, movie.budget, movie.genre_name,
            movie.poster_path, movie.release_date, movie.title, movie.vote_average, movie.vote_count)
}