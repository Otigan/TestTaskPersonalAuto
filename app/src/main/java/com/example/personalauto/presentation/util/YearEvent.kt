package com.example.personalauto.presentation.util

import com.example.personalauto.data.model.Year

sealed class YearEvent {
    data class Success(val years: List<Year>) : YearEvent()
    data class Error(val message: String) : YearEvent()
    object Loading : YearEvent()
}