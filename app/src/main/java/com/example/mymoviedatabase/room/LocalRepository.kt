package com.example.mymoviedatabase.room

import com.example.tmdbdata.Result

interface LocalRepository {
    fun getAllHistory(): List<Result>
    fun saveEntity(movie: Result)
}