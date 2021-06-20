package com.example.mymoviedatabase.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.mymoviedatabase.R
import com.example.tmdbdata.Result

class MDBAdapter (val movies: List<Result>, private val onClickListener: (View, Result) -> Unit): RecyclerView.Adapter<MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_fragment_recycler_item, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val moviesList = movies[position]

        holder.itemView.setOnClickListener { view ->
            onClickListener.invoke(view, moviesList)
        }

        return holder.bind(movies[position])
    }
}

class MoviesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val title:TextView = itemView.findViewById(R.id.main_name)
    private val rating:TextView = itemView.findViewById(R.id.main_rating)
    private val realised:TextView = itemView.findViewById(R.id.main_realesed)
    private val poster:ImageView = itemView.findViewById(R.id.main_poster)

    fun bind(movie: Result) {

            poster.load("http://image.tmdb.org/t/p/w500${movie.poster_path}") // Loading poster via Coil
            //Glide.with(itemView.context).load("http://image.tmdb.org/t/p/w500${movie.poster_path}").into(poster)
            title.text = "Title: " + movie.title
            realised.text = "Realised at : " + movie.release_date
            rating.text = "Rating : " + movie.vote_average
    }
}

