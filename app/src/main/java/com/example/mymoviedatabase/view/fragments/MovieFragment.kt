package com.example.mymoviedatabase.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mymoviedatabase.model.Movie
import com.example.mymoviedatabase.databinding.MovieFragmentBinding

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = MovieFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(BUNDLE_EXTRA)?.let { it ->
            binding.movieName.text = it.name
            binding.movieRealised.text = it.realisedAt.toString()
            binding.movieGenre.text = it.genre
            binding.movieRating.text = it.rating.toString()
            binding.movieDescription.text = it.description
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