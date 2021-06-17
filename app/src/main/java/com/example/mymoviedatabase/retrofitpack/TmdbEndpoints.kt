package com.example.retrofittest2

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbEndpoints {

    @GET("/3/movie/top_rated")
    fun getTopMovies(@Query("api_key") key: String, @Query("language") language: String, @Query("include_adult") adult: Boolean?): Call<Movies>

    @GET("/3/movie/popular")
    fun getPopularMovies(@Query("api_key") key: String, @Query("language") language: String, @Query("include_adult") adult: Boolean?): Call<Movies>

    @GET("/3/search/movie?api_key=db54cff977d7322cf1ce7a7e533b3721&language=ru&query=throat&page=1")
    fun getTestList(@Query("include_adult") adult: Boolean?): Call<Movies>

    //db54cff977d7322cf1ce7a7e533b3721
}