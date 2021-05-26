package com.example.mymoviedatabase.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymoviedatabase.model.Movie
import com.example.mymoviedatabase.databinding.MovieFragmentBinding
import com.example.mymoviedatabase.viewmodel.AppState
import com.example.mymoviedatabase.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MovieFragment: Fragment() {

    companion object {
        const val BUNDLE_EXTRA = "movie"
        fun newInstance(bundle: Bundle): MovieFragment {
            val fragment = MovieFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: MainViewModel

    private var _binding: MovieFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = MovieFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieData = arguments?.getParcelable<Movie>(BUNDLE_EXTRA)
        if (movieData != null) {
            binding.movieName.text = movieData.name
            binding.movieRealised.text = movieData.realisedAt.toString()
            binding.movieGenre.text = movieData.genre
            binding.movieRating.text = movieData.rating.toString()
            binding.movieDescription.text = movieData.description
        }
    }
}