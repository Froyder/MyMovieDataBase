package com.example.mymoviedatabase.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val movie: String,
    val releaseDate: Int,
    val rating: String
)