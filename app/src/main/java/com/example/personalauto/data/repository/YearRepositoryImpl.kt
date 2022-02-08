package com.example.personalauto.data.repository

import com.example.personalauto.data.data_source.YearDataSource
import com.example.personalauto.data.model.Year
import com.example.personalauto.domain.repository.YearRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class YearRepositoryImpl @Inject constructor(private val yearDataSource: YearDataSource) :
    YearRepository {

    override fun getYear(id: Int, name: String): Flow<List<Year>> = flow {
        emit(yearDataSource.getYear(id, name).years)
    }
}