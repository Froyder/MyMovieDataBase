package com.example.mymoviedatabase.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviedatabase.R
import com.example.mymoviedatabase.databinding.MainFragmentBinding
import com.example.mymoviedatabase.model.Movie
import com.example.mymoviedatabase.view.adapters.NewListAdapter
import com.example.mymoviedatabase.view.adapters.PopListAdapter
import com.example.mymoviedatabase.viewmodel.AppState
import com.example.mymoviedatabase.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    //private lateinit var viewModel: MainViewModel
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    //KOTLIN style
    private val newListAdapter = NewListAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(movie: Movie) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .replace(R.id.container, MovieFragment.newInstance(Bundle().apply {
                        putParcelable(MovieFragment.BUNDLE_EXTRA, movie)
                    }))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
            }
        }
    })

    //JAVA style
    private val popListAdapter = PopListAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(movie: Movie) {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                val bundle = Bundle()
                bundle.putParcelable(MovieFragment.BUNDLE_EXTRA, movie)
                manager.beginTransaction()
                    .replace(R.id.container, MovieFragment.newInstance(bundle))
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use the Kotlin extension in the fragment-ktx artifact
        setFragmentResultListener("request") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            val status = bundle.getString("key")
            // Do something with the result
            binding.statusTv.text = status
        }
    }

    override fun onDestroy() {
        newListAdapter.removeListener()
        popListAdapter.removeListener()
        super.onDestroy()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        val newListManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val popListManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.newListRecyclerView.layoutManager = newListManager
        binding.popListRecyclerView.layoutManager = popListManager

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newListRecyclerView.adapter = newListAdapter
        binding.popListRecyclerView.adapter = popListAdapter
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getMovieFromLocalSource()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                newListAdapter.setMovieList(appState.newMovieList)
                popListAdapter.setMovieList(appState.popMovieList)
            }
            is AppState.Loading -> {
                binding.newHeaderTv.visibility = View.VISIBLE
                Snackbar.make(binding.statusTv, "Loading...", Snackbar.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                binding.mainFragmentRootView.showSnackBar(
                        getString(R.string.error),
                        getString(R.string.reload),
                        { viewModel.getMovieFromLocalSource() })
            }
        }

    }

    private fun View.showSnackBar(
            text: String,
            actionText: String,
            action: (View) -> Unit,
            length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

}