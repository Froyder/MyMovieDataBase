package com.example.mymoviedatabase

interface Repository {

    fun getMovieFromServer(): Movie

    fun getMovieFromLocalStorage(): Movie
}