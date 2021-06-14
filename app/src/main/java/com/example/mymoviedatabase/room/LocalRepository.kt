package com.example.mymoviedatabase.room

import com.example.retrofittest2.Result

interface LocalRepository {
    fun getAllHistory(): List<Result>
    fun saveEntity(movie: Result)
}