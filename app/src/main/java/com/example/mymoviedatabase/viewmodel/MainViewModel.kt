package com.example.mymoviedatabase.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymoviedatabase.app.App.Companion.getHistoryDao
import com.example.mymoviedatabase.model.Repository
import com.example.mymoviedatabase.model.RepositoryImpl
import com.example.mymoviedatabase.room.HistoryEntity
import com.example.mymoviedatabase.room.LocalRepository
import com.example.mymoviedatabase.room.LocalRepositoryImpl
import com.example.tmdbdata.Result

class MainViewModel(
        private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
        private val repositoryImpl: Repository = RepositoryImpl(),
        private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())
)
    : ViewModel() {

    fun getLiveData() = liveDataToObserve
    fun getMovieFromLocalSource() = getDataFromLocalSource()
    fun getMovieFromRemoteSource() = getDataFromLocalSource()

    fun saveMovieToDB(movie: Result) {
        historyRepository.saveEntity(movie)
    }

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            liveDataToObserve.postValue(AppState.Success
                (newMovieList = repositoryImpl.getNewListFromLocalStorage(),
                popMovieList = repositoryImpl.getPopularListFromLocalStorage()))
        }.start()
    }

}