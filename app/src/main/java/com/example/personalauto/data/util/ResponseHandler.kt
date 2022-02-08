package com.example.personalauto.data.util

import android.content.Context
import com.example.personalauto.R
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1),
    NoInternetConnection(-2)
}

@Singleton
open class ResponseHandler @Inject constructor(@ApplicationContext private val context: Context) {

    fun <T> handleSuccess(data: T): Resource<T> {
        return Resource.Success(data)
    }

    fun <T> handleException(e: Exception, data: T?): Resource<T> {
        e.printStackTrace()
        return when (e) {
            is HttpException -> Resource.Error(getErrorMessage(e.code()), data)
            is IOException -> Resource.Error(
                getErrorMessage(ErrorCodes.NoInternetConnection.code),
                data
            )
            is SocketTimeoutException -> Resource.Error(
                getErrorMessage(ErrorCodes.SocketTimeOut.code),
                data
            )
            else -> Resource.Error(getErrorMessage(Int.MIN_VALUE), data)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> context.getString(R.string.error_timeout)
            ErrorCodes.NoInternetConnection.code -> context.getString(R.string.error_no_internet_connection)
            401 -> context.getString(R.string.error_401)
            403 -> context.getString(R.string.error_403)
            else -> context.getString(R.string.error_unknown)
        }
    }
}