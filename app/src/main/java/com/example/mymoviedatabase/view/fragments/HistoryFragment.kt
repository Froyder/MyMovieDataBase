package com.example.mymoviedatabase.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymoviedatabase.R
import com.example.mymoviedatabase.app.App
import com.example.mymoviedatabase.databinding.HistoryFragmentBinding
import com.example.mymoviedatabase.databinding.MovieFragmentBinding
import com.example.mymoviedatabase.room.LocalRepository
import com.example.mymoviedatabase.room.LocalRepositoryImpl
import com.example.mymoviedatabase.view.MainActivity
import com.example.mymoviedatabase.view.adapters.HistoryAdapter
import com.example.mymoviedatabase.viewmodel.HistoryViewModel
import com.example.retrofittest2.Result
import kotlinx.android.synthetic.main.history_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryFragment: Fragment() {

    val historyRepository: LocalRepository = LocalRepositoryImpl(App.getHistoryDao())

    companion object {
        fun newInstance(bundle: Bundle): HistoryFragment {
            val fragment = HistoryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: HistoryFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by lazy { ViewModelProvider(this).get(HistoryViewModel::class.java) }
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyFragmentRecyclerview.adapter = adapter

        runBlocking {
            launch {
                adapter.setData(historyRepository.getAllHistory(),activity = MainActivity())
            }
        }

    }
}