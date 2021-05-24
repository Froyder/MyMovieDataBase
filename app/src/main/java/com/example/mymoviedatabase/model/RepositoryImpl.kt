package com.example.mymoviedatabase.model

import com.example.mymoviedatabase.model.Repository

class RepositoryImpl : Repository {

    override fun getMovieFromServer(): Movie {
        return Movie()
    }

    override fun getMovieFromLocalStorage(): Movie {
        return getDefaultMovie()
    }


}