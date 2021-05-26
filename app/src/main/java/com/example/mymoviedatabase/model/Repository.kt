package com.example.mymoviedatabase.model

interface Repository {

    fun getMovieFromServer(): Movie

    fun getMovieListFromLocalStorage(): List<Movie>
}

