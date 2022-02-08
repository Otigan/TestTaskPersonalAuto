package com.example.personalauto.domain.repository

import com.example.personalauto.data.model.Year
import com.example.personalauto.data.model.Years
import kotlinx.coroutines.flow.Flow

interface YearRepository {

    fun getYear(id: Int, name: String): Flow<List<Year>>

}