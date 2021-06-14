package com.example.mymoviedatabase.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class HistoryEntity(

        @PrimaryKey(autoGenerate = true)
        //val date: Date?,
        val id: Int,
        val adult : Boolean,
        val overview: String,
        val budget: Int,
        val genre_name: String?,
        val poster_path: String,
        val release_date: String,
        val title: String,
        val vote_average: Double,
        val vote_count: Int
) : Parcelable