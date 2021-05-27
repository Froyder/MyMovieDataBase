package com.example.mymoviedatabase.model

import com.example.mymoviedatabase.model.Repository

class RepositoryImpl : Repository {

    override fun getMovieFromServer() = Movie()

    override fun getNewListFromLocalStorage() = getNewListFromStorage()

    override fun getPopularListFromLocalStorage() = getPopularListFromStorage()


}