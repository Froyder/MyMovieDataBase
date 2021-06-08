package com.example.retrofittest2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbEndpoints {

    @GET("/3/movie/top_rated")
    fun getTopMovies(@Query("api_key") key: String): Call<Movies>

    @GET("/3/movie/popular")
    fun getPopularMovies(@Query("api_key") key: String): Call<Movies>

}