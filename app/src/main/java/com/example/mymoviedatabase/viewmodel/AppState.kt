package com.example.mymoviedatabase.viewmodel

import com.example.mymoviedatabase.model.Movie

sealed class AppState {
    data class Success(val movieData: Movie) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
