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
import androidx.paging.LoadState
import com.example.personalauto.R
import com.example.personalauto.data.model.Auto
import com.example.personalauto.data.model.Manufacturer
import com.example.personalauto.databinding.FragmentAutoBinding
import com.example.personalauto.presentation.AutoViewModel
import com.example.personalauto.ui.adapter.AutoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AutoFragment : Fragment(R.layout.fragment_auto) {

    private var _binding: FragmentAutoBinding? = null
    private val binding get() = _binding!!
    private val autoViewModel by viewModels<AutoViewModel>()
    private val args by navArgs<AutoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAutoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manufacturer = args.manufacturer

        val autoAdapter = AutoAdapter(onClick = {
            navigateToYearFragment(manufacturer, it)
        })

        binding.apply {
            txtSelectedManufacturer.text =
                getString(
                    R.string.txt_selected_manufacturer, manufacturer
                )
            recyclerView.apply {
                setHasFixedSize(true)
                adapter = autoAdapter
            }
        }

        autoViewModel.getAutos(manufacturer.id)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    autoViewModel.autos.collectLatest {
                        autoAdapter.submitData(it)
                    }
                }
                launch {
                    autoAdapter.loadStateFlow.collectLatest { loadState ->
                        val isListEmpty =
                            loadState.refresh is LoadState.Error && autoAdapter.itemCount == 0

                        binding.apply {
                            txtViewError.isVisible = isListEmpty
                            recyclerView.isVisible =
                                loadState.refresh is LoadState.NotLoading
                            progressBar.isVisible = loadState.refresh is LoadState.Loading
                            btnRetry.isVisible =
                                loadState.refresh is LoadState.Error && autoAdapter.itemCount == 0
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

    private fun navigateToYearFragment(manufacturer: Manufacturer, auto: Auto) {
        val action = AutoFragmentDirections.actionAutoFragmentToYearFragment(manufacturer, auto)
        findNavController().navigate(action)
    }
}