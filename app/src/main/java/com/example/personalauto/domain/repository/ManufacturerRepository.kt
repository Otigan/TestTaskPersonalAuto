package com.example.personalauto.domain.repository

import androidx.paging.PagingData
import com.example.personalauto.data.model.Manufacturer
import kotlinx.coroutines.flow.Flow

interface ManufacturerRepository {

    fun getManufacturers(): Flow<PagingData<Manufacturer>>
}