package com.example.personalauto.data.api

import com.example.personalauto.data.model.Autos
import com.example.personalauto.data.model.Manufacturers
import com.example.personalauto.data.model.Years
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonalAutoApi {


    companion object {
        const val BASE_URL = "https://baseurl.com/"
    }

    @GET("manufacturers")
    suspend fun getManufacturers(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Manufacturers

    @GET("autos/{manufacturer_id}")
    suspend fun getAutos(
        @Path(value = "manufacturer_id", encoded = false) manufacturer_id: Int,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Autos

    @GET("year/{manufacturer_id}/{name}")
    suspend fun getYears(
        @Path(value = "manufacturer_id", encoded = false) manufacturer_id: Int,
        @Path(value = "name", encoded = false) name: String
    ): Years

}