package com.example.mymoviedatabase.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.mymoviedatabase.app.App
import com.example.mymoviedatabase.databinding.MovieFragmentBinding
import com.example.mymoviedatabase.room.HistoryEntity
import com.example.mymoviedatabase.room.LocalRepository
import com.example.mymoviedatabase.room.LocalRepositoryImpl
import com.example.mymoviedatabase.viewmodel.MainViewModel
import com.example.tmdbdata.Result
import kotlinx.android.synthetic.main.movie_fragment.*

class MovieFragment: Fragment() {

    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    val historyRepository: LocalRepository = LocalRepositoryImpl(App.getHistoryDao())

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
            context?.let { it1 -> poster.load("https://image.tmdb.org/t/p/original/${it.poster_path}")}
            binding.movieName.text = it.title
            binding.movieRealised.text = "Realised at: \n"+ it.release_date
            binding.movieRating.text = "Rating: "+ it.vote_average.toString()
            binding.movieDescription.text = it.overview
            saveMovie(it)
        }
    }

    private fun saveMovie(movie: Result) {
        viewModel.saveMovieToDB(
                Result(movie.id, movie.adult, movie.overview, movie.budget, movie.genre_name,
                        movie.poster_path, movie.release_date, movie.title, movie.vote_average, movie.vote_count)
        )
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