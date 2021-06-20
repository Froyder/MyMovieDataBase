package com.example.mymoviedatabase.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.bumptech.glide.Glide
import com.example.mymoviedatabase.R
import com.example.mymoviedatabase.databinding.ArtistsFragmentBinding
import com.example.mymoviedatabase.viewmodel.MainViewModel
import com.example.tmdbdata.ArtistResult
import com.example.tmdbdata.ArtistTest
import com.example.tmdbdata.Artists
import com.example.tmdbdata.Result
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.artists_fragment.*
import kotlinx.android.synthetic.main.movie_fragment.*

class ArtistFragment: Fragment() {

    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    companion object {
        const val BUNDLE_EXTRA = "artist"
        fun newInstance(bundle: Bundle): ArtistFragment {
            val fragment = ArtistFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: ArtistsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = ArtistsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<ArtistResult>(BUNDLE_EXTRA)?.let { it ->
            context?.let { it1 -> actorPhoto.load("https://image.tmdb.org/t/p/original/${it.poster_path}")}
            binding.artistName.text = it.name
            binding.dateOfBirth.text = "Realised at: \n" + it.birthday
            binding.popularity.text = "Rating: " + it.popularity.toString()
            binding.placeOfBirth.text = "Birth at: \n" + it.place_of_birth
            binding.biography.text = it.biography
            //saveArtist(it)

            val getCoordinates : String? = it.place_of_birth

            binding.placeOfBirth.setOnClickListener {

                setFragmentResult(
                        "placeOfBirth",
                        bundleOf("lat" to 54.99244, "long" to 73.36859, "coordinates" to getCoordinates)
                )

                activity?.supportFragmentManager?.apply {
                    beginTransaction()
                            .replace(R.id.container, GoogleMapsFragment())
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                }
            }
        }
    }

    private fun saveMovie(movie: Result) {
        viewModel.saveMovieToDB(
                Result(movie.id, movie.adult, movie.overview, movie.budget, movie.genre_name,
                        movie.poster_path, movie.release_date, movie.title, movie.vote_average, movie.vote_count)
        )
    }
}