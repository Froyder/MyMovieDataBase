package com.example.mymoviedatabase.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.mymoviedatabase.app.App
import com.example.mymoviedatabase.databinding.HistoryFragmentBinding
import com.example.mymoviedatabase.databinding.NoteFragmentBinding
import com.example.mymoviedatabase.room.HistoryEntity
import com.example.mymoviedatabase.room.LocalRepository
import com.example.mymoviedatabase.room.LocalRepositoryImpl
import com.example.mymoviedatabase.view.MainActivity
import com.example.mymoviedatabase.view.adapters.HistoryAdapter
import com.example.mymoviedatabase.viewmodel.HistoryViewModel
import com.example.mymoviedatabase.viewmodel.MainViewModel
import com.example.retrofittest2.Result
import kotlinx.android.synthetic.main.history_fragment.*
import kotlinx.android.synthetic.main.note_fragment.*

class NoteFragment: Fragment() {

    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    val historyRepository: LocalRepository = LocalRepositoryImpl(App.getHistoryDao())

    companion object {
        const val BUNDLE_EXTRA = "movie"
        fun newInstance(bundle: Bundle): NoteFragment {
            val fragment = NoteFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: NoteFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = NoteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<HistoryEntity>(BUNDLE_EXTRA)?.let { it ->
            context?.let { it1 -> poster.load("http://image.tmdb.org/t/p/w500${it.poster_path}")}
            binding.movieTitle.text =  String.format("\"%s\"", it.title)
            binding.movieRealised.text = "Realised at: \n"+ it.release_date
            binding.movieRating.text = "Rating: "+ it.vote_average.toString()

            saveMovie(it)
        }
    }

    private fun saveMovie(movie: HistoryEntity) {
        viewModel.saveMovieToDB(
                com.example.retrofittest2.Result(movie.id, movie.adult, movie.overview, movie.budget, movie.genre_name,
                        movie.poster_path, movie.release_date, movie.title, movie.vote_average, movie.vote_count)
        )
    }

}