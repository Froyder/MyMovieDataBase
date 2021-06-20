package com.example.mymoviedatabase.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.mymoviedatabase.R
import com.example.tmdbdata.ArtistResult
import com.example.tmdbdata.Result

class ArtistsApapter (val artists: List<ArtistResult>, private val onClickListener: (View, ArtistResult) -> Unit): RecyclerView.Adapter<ArtistsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_fragment_recycler_item, parent, false)
        return ArtistsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    override fun onBindViewHolder(holder: ArtistsViewHolder, position: Int) {
        val artistsList = artists[position]

        holder.itemView.setOnClickListener { view ->
            onClickListener.invoke(view, artistsList)
        }

        return holder.bind(artists[position])
    }
}

class ArtistsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val title: TextView = itemView.findViewById(R.id.main_name)
    private val rating: TextView = itemView.findViewById(R.id.main_rating)
    private val realised: TextView = itemView.findViewById(R.id.main_realesed)
    private val poster: ImageView = itemView.findViewById(R.id.main_poster)

    fun bind(artist: ArtistResult) {

        //poster.load("http://image.tmdb.org/t/p/w500${artist.poster_path}") // Loading poster via Coil
        //Glide.with(itemView.context).load("http://image.tmdb.org/t/p/w500${movie.poster_path}").into(poster)
        title.text = "Title: " + artist.name
        realised.text = "Realised at : " + artist.popularity
        rating.text = "Rating : " + artist.popularity
    }
}