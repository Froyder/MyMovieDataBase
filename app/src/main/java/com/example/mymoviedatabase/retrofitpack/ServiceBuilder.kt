package com.example.retrofittest2

import android.os.Bundle
import com.example.mymoviedatabase.view.fragments.MovieFragment
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient(MovieApiInterceptor.newInstance()))
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

}

class MovieApiInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        return chain.proceed(chain.request())
    }

    companion object movieInterceptor {
        fun newInstance(): MovieApiInterceptor {
            val movieInterceptor = MovieApiInterceptor()
            return movieInterceptor
        }
    }
}