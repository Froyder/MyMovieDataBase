package com.example.mymoviedatabase

data class Movie(
    val name : String = "",
    val realisedAt: Int = 0,
    val genre : String = "",
    val rating : Double = 0.0
)

fun getDefaultMovie() = Movie("Citizen Kane", 1941, "drama", 8.8 )