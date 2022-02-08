package com.example.personalauto.data.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.personalauto.data.api.PersonalAutoApi
import com.example.personalauto.data.model.Manufacturer
import retrofit2.HttpException
import java.io.IOException

class ManufacturerPagingSource(private val api: PersonalAutoApi) :
    PagingSource<Int, Manufacturer>() {

    override fun getRefreshKey(state: PagingState<Int, Manufacturer>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Manufacturer> {
        val position = params.key ?: 1
        return try {
            val response = api.getManufacturers(position, params.loadSize)
            LoadResult.Page(
                response.manufacturers,
                prevKey = if (position == 1) null else position.minus(1),
                nextKey = if (response.manufacturers.isEmpty()) null else position.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}