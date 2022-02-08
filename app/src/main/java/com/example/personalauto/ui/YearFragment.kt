package com.example.personalauto.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.personalauto.R
import com.example.personalauto.data.model.Auto
import com.example.personalauto.data.model.Manufacturer
import com.example.personalauto.data.model.Year
import com.example.personalauto.databinding.FragmentYearBinding
import com.example.personalauto.presentation.YearViewModel
import com.example.personalauto.ui.adapter.YearAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class YearFragment : Fragment(R.layout.fragment_year) {


    private var _binding: FragmentYearBinding? = null
    private val binding get() = _binding!!
    private val yearViewModel by viewModels<YearViewModel>()
    private val args by navArgs<YearFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYearBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manufacturer = args.manufacturer
        val auto = args.auto

        val yearAdapter = YearAdapter(onClick = {
            navigateToFinalScreen(manufacturer, auto, it)
        })



        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                yearViewModel.getYear(manufacturer.id, auto.name).collectLatest {
                    yearAdapter.submitList(it)
                }
            }
        }

        binding.apply {
            recyclerView.apply {
                setHasFixedSize(true)
                adapter = yearAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navigateToFinalScreen(manufacturer: Manufacturer, auto: Auto, year: Year) {

    }
}