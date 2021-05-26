package com.example.mymoviedatabase.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.example.mymoviedatabase.databinding.MovieFragmentBinding
import com.example.mymoviedatabase.databinding.SettingsFragmentBinding
import com.example.mymoviedatabase.viewmodel.MainViewModel

class SettingsFragment: Fragment() {

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.acceptButton.setOnClickListener {
            var selected : Int = binding.radioGroup.checkedRadioButtonId
            if (selected.equals(binding.radioButton1.id)) {
                setFragmentResult(
                    "request",
                    bundleOf("key" to "Button 1")
                )
            } else if (selected.equals(binding.radioButton2.id)) {
                setFragmentResult(
                    "request",
                    bundleOf("key" to "Button 2")
                )
            } else if (selected.equals(binding.radioButton3.id)) {
                setFragmentResult(
                    "request",
                    bundleOf("key" to "Button 3")
                )
            }
            activity?.supportFragmentManager?.popBackStack()
        }
    }
}