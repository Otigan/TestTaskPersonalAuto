package com.example.personalauto.presentation

import androidx.lifecycle.ViewModel
import com.example.personalauto.domain.use_case.GetYearUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YearViewModel @Inject constructor(private val getYearUseCase: GetYearUseCase) : ViewModel() {

    fun getYear(id: Int, name: String) = getYearUseCase(id, name)

}