package com.example.mymoviedatabase.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import com.bumptech.glide.Glide
import com.example.mymoviedatabase.model.Movie
import com.example.mymoviedatabase.databinding.MovieFragmentBinding
import com.example.retrofittest2.Result
import kotlinx.android.synthetic.main.movie_fragment.*

class MovieFragment: Fragment() {

    companion object {
        const val BUNDLE_EXTRA = "movie"
        fun newInstance(bundle: Bundle): MovieFragment {
            val fragment = MovieFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: MovieFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = MovieFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Result>(BUNDLE_EXTRA)?.let { it ->
            context?.let { it1 -> poster.load("http://image.tmdb.org/t/p/w500${it.poster_path}")}
            binding.movieName.text = it.title
            binding.movieRealised.text = "Realised at: \n"+ it.release_date
            binding.movieRating.text = "Rating: "+ it.vote_average.toString()
            binding.movieDescription.text = it.overview
        }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val movieData = arguments?.getParcelable<Movie>(BUNDLE_EXTRA)
//        if (movieData != null) {
//            binding.movieName.text = movieData.name
//            binding.movieRealised.text = movieData.realisedAt.toString()
//            binding.movieGenre.text = movieData.genre
//            binding.movieRating.text = movieData.rating.toString()
//            binding.movieDescription.text = movieData.description
//        }
//    }
}