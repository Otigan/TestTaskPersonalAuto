package com.example.personalauto.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.personalauto.R
import com.example.personalauto.databinding.FragmentConfirmSelectBinding
import com.google.android.material.snackbar.Snackbar

class ConfirmSelectFragment : Fragment(R.layout.fragment_confirm_select) {

    private var _binding: FragmentConfirmSelectBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<ConfirmSelectFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConfirmSelectBinding.inflate(inflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manufacturer = args.manufacturer
        val auto = args.auto
        val year = args.year

        binding.apply {
            txtSelectedManufacturer.text =
                getString(R.string.txt_selected_manufacturer, manufacturer)
            txtSelectedAuto.text = getString(R.string.txt_selected_auto, auto)
            txtSelectedYear.text = getString(R.string.txt_selected_year, year)

            btnConfirmChoice.setOnClickListener {
                Snackbar.make(
                    binding.root,
                    getString(R.string.confirm_success),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}