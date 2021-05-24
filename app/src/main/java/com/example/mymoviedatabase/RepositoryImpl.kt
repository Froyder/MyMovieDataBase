package com.example.mymoviedatabase

class RepositoryImpl : Repository {

    override fun getMovieFromServer(): Movie {
        return Movie()
    }

    override fun getMovieFromLocalStorage(): Movie {
        return getDefaultMovie()
    }


}