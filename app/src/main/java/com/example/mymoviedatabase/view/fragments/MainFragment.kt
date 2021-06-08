package com.example.mymoviedatabase.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
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
import com.example.retrofittest2.*
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    //private lateinit var viewModel: MainViewModel
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use the Kotlin extension in the fragment-ktx artifact
        setFragmentResultListener("request") { requestKey, bundle ->
            when (bundle.getString("key")) {
                "Only popular" -> {
                    binding.topListRecyclerView.hide()
                    binding.topHeaderTv.hide()
                }
                "Only top" -> {
                    binding.popListRecyclerView.hide()
                    binding.popularHeaderTv.hide()
                }
                else -> null
            }
            binding.statusTv.text = bundle.getString("key")
        }
    }

    fun View.show() : View {
        if (visibility != View.VISIBLE) {
            visibility = View.VISIBLE
        }
        return this
    }

    fun View.hide() : View {
        if (visibility != View.GONE) {
            visibility = View.GONE
        }
        return this
    }

    override fun onDestroy() {
        //newListAdapter.removeListener()
        //popListAdapter.removeListener()
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

        //binding.newListRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

       initPopList()

       initTopList()

        return binding.root
    }

    private fun initTopList() {
        val request = ServiceBuilder.buildService(TmdbEndpoints::class.java)
        val call = request.getTopMovies(getString(R.string.api_key))

        call.enqueue(object : Callback<Movies>{

            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if (response.isSuccessful){
                    binding.topListRecyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                        adapter = PopListAdapter(response.body()!!.results, onClickListener = { view, movie ->
                            activity?.supportFragmentManager?.apply {
                                beginTransaction()
                                        .replace(R.id.container, MovieFragment.newInstance(Bundle().apply {
                                            Toast.makeText(context, "Load Movie Page", Toast.LENGTH_SHORT).show()
                                            putParcelable(MovieFragment.BUNDLE_EXTRA, movie)
                                        }))
                                        .addToBackStack("")
                                        .commitAllowingStateLoss()
                            }})
                    }
                }
            }
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initPopList() {
        val request = ServiceBuilder.buildService(TmdbEndpoints::class.java)
        val call = request.getPopularMovies(getString(R.string.api_key))

        call.enqueue(object : Callback<Movies>{

            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if (response.isSuccessful){
                    binding.popListRecyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                        adapter = PopListAdapter(response.body()!!.results, onClickListener = { view, movie ->
                            activity?.supportFragmentManager?.apply {
                                    beginTransaction()
                                    .replace(R.id.container, MovieFragment.newInstance(Bundle().apply {
                                        Toast.makeText(context, "Load Movie Page", Toast.LENGTH_SHORT).show()
                                        putParcelable(MovieFragment.BUNDLE_EXTRA, movie)
                                    }))
                                    .addToBackStack("")
                                    .commitAllowingStateLoss()
                        }})
                    }
                }
            }
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.newListRecyclerView.adapter = newListAdapter
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getMovieFromLocalSource()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                //newListAdapter.setMovieList(appState.newMovieList)
                //popListAdapter.setMovieList(appState.popMovieList)
            }
            is AppState.Loading -> {
                Snackbar.make(binding.statusTv, "Loading...", Snackbar.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                binding.mainFragmentRootView.snackBarCreateAndShow(
                    getString(R.string.reload),
                    { viewModel.getMovieFromLocalSource() })
            }
        }

    }

    //KOTLIN style for LOCAL SOURCE
//    private val newListAdapter = NewListAdapter(object : OnItemViewClickListener {
//        override fun onItemViewClick(movie: Movie) {
//            activity?.supportFragmentManager?.apply {
//                beginTransaction()
//                        .replace(R.id.container, MovieFragment.newInstance(Bundle().apply {
//                            putParcelable(MovieFragment.BUNDLE_EXTRA, movie)
//                        }))
//                        .addToBackStack("")
//                        .commitAllowingStateLoss()
//            }
//        }
//    })

//    private fun View.showSnackBar(
//            text: String,
//            actionText: String,
//            action: (View) -> Unit,
//            length: Int = Snackbar.LENGTH_INDEFINITE
//    ) {
//        Snackbar.make(this, text, length).setAction(actionText, action).show()
//    }

    fun View.snackBarCreateAndShow(
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, R.string.error, length).setAction(actionText, action).show()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

}