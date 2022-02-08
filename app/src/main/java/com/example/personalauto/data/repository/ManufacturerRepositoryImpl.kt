package com.example.personalauto.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.personalauto.data.api.PersonalAutoApi
import com.example.personalauto.data.data_source.ManufacturerPagingSource
import com.example.personalauto.data.model.Manufacturer
import com.example.personalauto.domain.repository.ManufacturerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ManufacturerRepositoryImpl @Inject constructor(private val api: PersonalAutoApi) :
    ManufacturerRepository {


    override fun getManufacturers(): Flow<PagingData<Manufacturer>> =
        Pager(
            PagingConfig(
                pageSize = 15,
                maxSize = 60,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ManufacturerPagingSource(api) }
        ).flow

}