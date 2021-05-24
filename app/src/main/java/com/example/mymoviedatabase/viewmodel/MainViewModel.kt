package com.example.mymoviedatabase.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymoviedatabase.Repository
import com.example.mymoviedatabase.RepositoryImpl

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
)
    : ViewModel() {

    fun getLiveData() = liveDataToObserve
    fun getMovieFromLocalSource() = getDataFromLocalSource()
    fun getMovieFromRemoteSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getMovieFromLocalStorage()))
        }.start()
    }

    fun userClicked() {
        //counter++
    }

}