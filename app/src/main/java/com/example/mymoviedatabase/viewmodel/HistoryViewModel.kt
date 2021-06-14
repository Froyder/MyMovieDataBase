package com.example.mymoviedatabase.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymoviedatabase.app.App.Companion.getHistoryDao
import com.example.mymoviedatabase.room.LocalRepository
import com.example.mymoviedatabase.room.LocalRepositoryImpl

class HistoryViewModel(
        val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
        private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())
) : ViewModel() {

    fun getAllHistory() {
      historyRepository.getAllHistory()
    }
}