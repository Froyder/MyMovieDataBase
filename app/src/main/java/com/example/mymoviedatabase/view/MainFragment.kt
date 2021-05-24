package com.example.mymoviedatabase.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.mymoviedatabase.viewmodel.AppState
import com.example.mymoviedatabase.model.Movie
import com.example.mymoviedatabase.R
import com.example.mymoviedatabase.databinding.MainFragmentBinding
import com.example.mymoviedatabase.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = MainFragmentBinding.inflate(inflater, container, false)
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

        binding.newList.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.container, MovieFragment())
                .addToBackStack(null).commit()
        }

        binding.popularList.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.container, MovieFragment())
                .addToBackStack(null).commit()
        }

    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val movieData = appState.movieData
                setData(movieData)
                setDataTest(movieData)
                Snackbar.make(binding.statusTv, "Success!", Snackbar.LENGTH_SHORT).show()
                binding.statusTv.text = getString(R.string.load_complete)
            }
            is AppState.Loading -> {
                binding.newHeaderTv.visibility = View.VISIBLE
                Snackbar.make(binding.statusTv, "Loading...", Snackbar.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                Snackbar
                        .make(binding.statusTv, "Error", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Reload") { viewModel.getLiveData() }
                        .show()
            }
        }

    }

    private fun setData(movieData: Movie) {
        binding.new1Name.text = movieData.name
        binding.new1Realesed.text = movieData.realisedAt.toString()
        binding.new1Genre.text = movieData.genre
    }

    private fun setDataTest(movieData: Movie) {
        binding.pop1Name.text = movieData.name
        binding.pop1Realesed.text = movieData.realisedAt.toString()
        binding.pop1Genre.text = movieData.genre
    }

//    private fun push () {
//        Toast.makeText(context, "new", Toast.LENGTH_LONG).show()
//    }

}