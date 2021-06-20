package com.example.mymoviedatabase.room

import com.example.tmdbdata.Result
import com.example.tmdbdata.convertHistoryEntityToMovie
import com.example.tmdbdata.convertMovieToEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDao) :
    LocalRepository {

    override fun getAllHistory(): List<Result> {
        return convertHistoryEntityToMovie(localDataSource.all())
    }

    override fun saveEntity(movie: Result) {
        localDataSource.insert(convertMovieToEntity(movie))
    }
}