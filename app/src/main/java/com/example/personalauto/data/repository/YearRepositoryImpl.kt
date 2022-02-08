package com.example.personalauto.data.repository

import com.example.personalauto.data.data_source.YearDataSource
import com.example.personalauto.data.model.Year
import com.example.personalauto.data.util.Resource
import com.example.personalauto.data.util.ResponseHandler
import com.example.personalauto.domain.repository.YearRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class YearRepositoryImpl @Inject constructor(
    private val yearDataSource: YearDataSource,
    private val responseHandler: ResponseHandler
) :
    YearRepository {

    override fun getYear(id: Int, name: String): Flow<Resource<List<Year>>> = flow {
        try {
            emit(Resource.Loading())
            val response = yearDataSource.getYears(id, name)
            emit(responseHandler.handleSuccess(response.years))
        } catch (e: Exception) {
            emit(responseHandler.handleException(e, null))
        }
    }
}