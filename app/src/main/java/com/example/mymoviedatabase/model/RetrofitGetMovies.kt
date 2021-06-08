package com.example.mymoviedatabase.model

import android.widget.TextView
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitGetMovies {
        @GET("3/movie/550?api_key=db54cff977d7322cf1ce7a7e533b3721")
        fun getMovieFromServer(@Query("title") movieName: String): Call<Movie>

}

