package com.example.personalauto.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.personalauto.data.model.Manufacturer
import com.example.personalauto.domain.use_case.GetManufacturersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManufacturerViewModel @Inject constructor(private val getManufacturersUseCase: GetManufacturersUseCase) :
    ViewModel() {

    private val _manufacturers = MutableStateFlow<PagingData<Manufacturer>>(PagingData.empty())
    val manufacturers = _manufacturers.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getManufacturersUseCase().cachedIn(viewModelScope).collectLatest {
                _manufacturers.value = it
            }
        }
    }

}