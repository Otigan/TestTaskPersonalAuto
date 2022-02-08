package com.example.personalauto.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalauto.data.util.Resource
import com.example.personalauto.domain.use_case.GetYearUseCase
import com.example.personalauto.presentation.util.YearEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YearViewModel @Inject constructor(private val getYearUseCase: GetYearUseCase) : ViewModel() {

    private val yearsEventChannel = Channel<YearEvent>(Channel.BUFFERED)
    val yearsEventFlow = yearsEventChannel.receiveAsFlow()

    fun getYears(id: Int, name: String) = viewModelScope.launch(Dispatchers.IO) {
        getYearUseCase(id, name).collectLatest { resource ->
            when (resource) {
                is Resource.Error -> {
                    resource.errorMessage?.let {
                        yearsEventChannel.send(YearEvent.Error(it))
                    }
                }
                is Resource.Loading -> yearsEventChannel.send(YearEvent.Loading)
                is Resource.Success -> {
                    resource.data?.let {
                        yearsEventChannel.send(YearEvent.Success(it))
                    }
                }
            }
        }
    }

}