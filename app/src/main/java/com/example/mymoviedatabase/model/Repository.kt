package com.example.mymoviedatabase.model

interface Repository {

    fun getMovieFromServer(): Movie

    fun getMovieFromLocalStorage(): Movie
}