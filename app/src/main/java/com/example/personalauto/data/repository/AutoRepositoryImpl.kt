package com.example.personalauto.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.personalauto.data.api.PersonalAutoApi
import com.example.personalauto.data.data_source.AutoPagingSource
import com.example.personalauto.data.model.Auto
import com.example.personalauto.domain.repository.AutoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AutoRepositoryImpl @Inject constructor(private val api: PersonalAutoApi) : AutoRepository {

    override fun getAutos(id: Int): Flow<PagingData<Auto>> =
        Pager(
            PagingConfig(
                pageSize = 15,
                maxSize = 60,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { AutoPagingSource(api, id) }
        ).flow
}