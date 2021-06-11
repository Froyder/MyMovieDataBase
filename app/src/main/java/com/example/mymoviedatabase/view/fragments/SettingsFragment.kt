package com.example.mymoviedatabase.view.fragments

import android.content.Context
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
            val listSettings : Int = binding.radioGroup.checkedRadioButtonId

            val adultSettings : Int = binding.radioGroup2.checkedRadioButtonId

            if (listSettings.equals(binding.radioButton1.id)) {
                setFragmentResult(
                    "request",
                    bundleOf("key" to "Only popular")
                )

                activity?.let {
                    with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                        putString(LIST_SETTINGS, "blue")
                        apply()
                    }
                }

            } else if (listSettings.equals(binding.radioButton2.id)) {
                setFragmentResult(
                    "request",
                    bundleOf("key" to "Only top")
                )

                activity?.let {
                    with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                        putString(LIST_SETTINGS, "green")
                        apply()
                    }
                }
            }else if (listSettings.equals(binding.radioButton3.id)) {
                setFragmentResult(
                    "request",
                    bundleOf("key" to "Both lists")
                )
            }

            if (adultSettings.equals(binding.radioButton4.id)) {
                activity?.let {
                    with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                       putString(ADULT_SETTINGS, "Adult On")
                       putBoolean("ADULT_CONTENT", true)
                       apply()
                    }
                }
            } else if (adultSettings.equals(binding.radioButton5.id)) {
                activity?.let {
                    with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                        putString(ADULT_SETTINGS, "Adult Off")
                        putBoolean("ADULT_CONTENT", false)
                        apply()
                    }
                }
            }

            activity?.supportFragmentManager?.popBackStack()
        }
    }
}