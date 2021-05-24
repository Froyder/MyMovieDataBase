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
        fun newInstance() = MovieFragment()
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getMovieFromLocalSource()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val movieData = appState.movieData
                setData(movieData)
            }
            is AppState.Loading -> {
                binding.movieDescription.visibility = View.VISIBLE
                Snackbar.make(binding.movieDescription, "Loading...", Snackbar.LENGTH_LONG).show()
            }
            is AppState.Error -> {
                binding.movieDescription.visibility = View.GONE
                Snackbar
                    .make(binding.movieDescription, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getLiveData() }
                    .show()
            }
        }

    }

    private fun setData(movieData: Movie) {
        binding.movieName.text = movieData.name
        binding.movieRealised.text = movieData.realisedAt.toString()
        binding.movieGenre.text = movieData.genre
        binding.movieRating.text = movieData.rating.toString()
        binding.movieDescription.text = movieData.description
    }


}