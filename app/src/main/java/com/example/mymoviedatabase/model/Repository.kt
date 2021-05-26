package com.example.mymoviedatabase.model

interface Repository {

    fun getMovieFromServer(): Movie

    fun getNewListFromLocalStorage(): List<Movie>

    fun getPopularListFromLocalStorage(): List<Movie>
}

