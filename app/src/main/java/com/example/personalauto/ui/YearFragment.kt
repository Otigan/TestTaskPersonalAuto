package com.example.personalauto.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.personalauto.R
import com.example.personalauto.data.model.Auto
import com.example.personalauto.data.model.Manufacturer
import com.example.personalauto.data.model.Year
import com.example.personalauto.databinding.FragmentYearBinding
import com.example.personalauto.presentation.YearViewModel
import com.example.personalauto.presentation.util.YearEvent
import com.example.personalauto.ui.adapter.YearAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manufacturer = args.manufacturer
        val auto = args.auto

        val yearAdapter = YearAdapter(onClick = {
            navigateToFinalScreen(manufacturer, auto, it)
        })

        binding.apply {
            btnRetry.setOnClickListener {
                yearViewModel.getYears(manufacturer.id, auto.name)
                yearAdapter.notifyDataSetChanged()
            }
            txtSelectedManufacturer.text =
                getString(R.string.txt_selected_manufacturer, manufacturer)

            txtSelectedAuto.text = getString(R.string.txt_selected_auto, auto)

            recyclerView.apply {
                setHasFixedSize(true)
                adapter = yearAdapter
            }
        }

        yearViewModel.getYears(manufacturer.id, auto.name)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                yearViewModel.yearsEventFlow.collectLatest { event ->
                    when (event) {
                        is YearEvent.Error -> {
                            binding.apply {
                                progressBar.isVisible = false
                                recyclerView.isVisible = false
                                btnRetry.isVisible = true
                                txtViewError.apply {
                                    isVisible = true
                                    text = event.message
                                }
                            }
                        }
                        is YearEvent.Loading -> {
                            binding.apply {
                                progressBar.isVisible = true
                                recyclerView.isVisible = false
                                btnRetry.isVisible = false
                                txtViewError.isVisible = false
                            }
                        }
                        is YearEvent.Success -> {
                            binding.apply {
                                progressBar.isVisible = false
                                recyclerView.isVisible = true
                                btnRetry.isVisible = false
                                txtViewError.isVisible = false
                                yearAdapter.submitList(event.years)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToFinalScreen(manufacturer: Manufacturer, auto: Auto, year: Year) {
        val action = YearFragmentDirections.actionYearFragmentToConfirmSelectFragment(
            auto,
            manufacturer,
            year
        )
        findNavController().navigate(action)
    }
}