package com.example.mymoviedatabase.room

import com.example.retrofittest2.Result
import com.example.retrofittest2.convertHistoryEntityToMovie
import com.example.retrofittest2.convertMovieToEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDao) :
    LocalRepository {

    override fun getAllHistory(): List<Result> {
        return convertHistoryEntityToMovie(localDataSource.all())
    }

    override fun saveEntity(movie: Result) {
        localDataSource.insert(convertMovieToEntity(movie))
    }
}