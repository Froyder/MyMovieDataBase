package com.example.retrofittest2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Movies(
    val results: List<Result>
)

@Parcelize
data class Result(
    val id: Int,
    val adult : Boolean,
    val overview: String,
    val budget: Int,
    val genre_name: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
) : Parcelable