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
import androidx.paging.LoadState
import com.example.personalauto.R
import com.example.personalauto.data.model.Manufacturer
import com.example.personalauto.databinding.FragmentManufacturerBinding
import com.example.personalauto.presentation.ManufacturerViewModel
import com.example.personalauto.ui.adapter.ManufacturerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManufacturerFragment : Fragment(R.layout.fragment_manufacturer) {

    private var _binding: FragmentManufacturerBinding? = null
    private val binding get() = _binding!!
    private val manufacturerViewModel by viewModels<ManufacturerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManufacturerBinding.inflate(inflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manufacturerAdapter = ManufacturerAdapter(onClick = {
            navigateToAutoSelect(it)
        })

        binding.apply {
            recyclerView.apply {
                adapter = manufacturerAdapter
                setHasFixedSize(true)
            }
            btnRetry.setOnClickListener {
                manufacturerAdapter.refresh()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    manufacturerViewModel.manufacturers.collectLatest {
                        manufacturerAdapter.submitData(it)
                    }
                }

                launch {
                    manufacturerAdapter.loadStateFlow.collectLatest { loadState ->
                        val isListEmpty =
                            loadState.refresh is LoadState.Error && manufacturerAdapter.itemCount == 0

                        binding.apply {
                            txtViewError.isVisible = isListEmpty
                            recyclerView.isVisible =
                                loadState.refresh is LoadState.NotLoading
                            progressBar.isVisible = loadState.refresh is LoadState.Loading
                            btnRetry.isVisible =
                                loadState.refresh is LoadState.Error && manufacturerAdapter.itemCount == 0
                        }

                        val errorState = loadState.prepend as? LoadState.Error
                            ?: loadState.append as? LoadState.Error
                            ?: loadState.refresh as? LoadState.Error

                        errorState?.let {
                            binding.txtViewError.text = getString(R.string.error_unknown)
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

    private fun navigateToAutoSelect(manufacturer: Manufacturer) {
        val action =
            ManufacturerFragmentDirections.actionManufacturerFragmentToAutoFragment(manufacturer)
        findNavController().navigate(action)
    }

}