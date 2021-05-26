package com.example.mymoviedatabase.model

import com.example.mymoviedatabase.model.Repository

class RepositoryImpl : Repository {

    override fun getMovieFromServer(): Movie {
        return Movie()
    }

    override fun getNewListFromLocalStorage(): List<Movie>{
        return getNewListFromStorage()
    }

    override fun getPopularListFromLocalStorage(): List<Movie> {
        return getPopularListFromStorage()
    }


}