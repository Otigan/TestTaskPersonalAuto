package com.example.personalauto.data.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.personalauto.data.api.PersonalAutoApi
import com.example.personalauto.data.model.Auto
import retrofit2.HttpException
import java.io.IOException

class AutoPagingSource(private val api: PersonalAutoApi, private val manufacturer_id: Int) :
    PagingSource<Int, Auto>() {

    override fun getRefreshKey(state: PagingState<Int, Auto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Auto> {
        val position = params.key ?: 1
        return try {
            val response = api.getAutos(manufacturer_id, position, params.loadSize)
            LoadResult.Page(
                response.autos,
                prevKey = if (position == 1) null else position.minus(1),
                nextKey = if (response.autos.isEmpty()) null else position.plus(1)
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}