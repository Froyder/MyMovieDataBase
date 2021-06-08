package com.example.mymoviedatabase.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviedatabase.R
import com.example.retrofittest2.Result


class PopListAdapter(val movies: List<Result>, private val onClickListener: (View, Result) -> Unit): RecyclerView.Adapter<MoviesViewHolder>() {


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

    fun bind(movie: Result) {
        title.text = "Title: "+ movie.title
        realised.text = "Realised at : "+ movie.release_date
        rating.text = "Rating : " + movie.vote_average

    }

}

