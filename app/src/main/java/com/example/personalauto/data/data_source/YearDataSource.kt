package com.example.personalauto.data.data_source

import com.example.personalauto.data.api.PersonalAutoApi
import javax.inject.Inject

class YearDataSource @Inject constructor(private val api: PersonalAutoApi) {

    suspend fun getYear(id: Int, name: String) = api.getYear(id, name)

}