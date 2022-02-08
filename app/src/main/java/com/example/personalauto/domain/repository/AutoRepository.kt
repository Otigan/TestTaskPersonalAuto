package com.example.personalauto.domain.repository

import androidx.paging.PagingData
import com.example.personalauto.data.model.Auto
import kotlinx.coroutines.flow.Flow

interface AutoRepository {

    fun getAutos(id: Int): Flow<PagingData<Auto>>
}