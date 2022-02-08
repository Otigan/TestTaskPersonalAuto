package com.example.personalauto.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.personalauto.data.model.Auto
import com.example.personalauto.domain.use_case.GetAutosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutoViewModel @Inject constructor(private val getAutosUseCase: GetAutosUseCase) :
    ViewModel() {

    private val _autos = MutableStateFlow<PagingData<Auto>>(PagingData.empty())
    val autos = _autos.asStateFlow()

    fun getAutos(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getAutosUseCase(id).cachedIn(viewModelScope).collectLatest {
                _autos.value = it
            }
        }
    }

}