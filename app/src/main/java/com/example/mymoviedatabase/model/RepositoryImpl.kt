package com.example.mymoviedatabase.model

import com.example.mymoviedatabase.model.Repository

class RepositoryImpl : Repository {

    override fun getMovieFromServer(): Movie {
        return Movie()
    }

    override fun getMovieListFromLocalStorage(): List<Movie> {
        return getPopularList()
    }


}