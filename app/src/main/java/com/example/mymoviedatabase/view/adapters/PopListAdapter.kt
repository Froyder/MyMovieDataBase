package com.example.mymoviedatabase.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviedatabase.R
import com.example.mymoviedatabase.model.Movie
import com.example.mymoviedatabase.view.fragments.MainFragment

class PopListAdapter (private var onItemViewClickListener: MainFragment.OnItemViewClickListener?):
    RecyclerView.Adapter<PopListAdapter.MainViewHolder>() {

    private var movieData: List<Movie> = listOf()

    fun setMovieList(data: List<Movie>) {
        movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_fragment_recycler_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(movieData[position])
    }

    override fun getItemCount(): Int {
        return movieData.size
    }

    fun removeListener() {
        onItemViewClickListener = null
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) {
            itemView.apply {
                findViewById<TextView>(R.id.main_name).text = movie.name
                findViewById<TextView>(R.id.main_realesed).text = movie.realisedAt.toString()
                findViewById<TextView>(R.id.main_genre).text = movie.genre

                setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(movie)
                }
            }
        }

//        fun bind(movie: Movie) {
//            itemView.findViewById<TextView>(R.id.main_name).text = movie.name
//            itemView.findViewById<TextView>(R.id.main_realesed).text = movie.realisedAt.toString()
//            itemView.findViewById<TextView>(R.id.main_genre).text = movie.genre
//
//            itemView.setOnClickListener {
//                onItemViewClickListener?.onItemViewClick(movie)
//            }
//        }
    }
}