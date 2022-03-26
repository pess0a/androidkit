package com.pess0a.androidkit.core.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection
import kotlin.coroutines.CoroutineContext

class ApiExecutor(private val context: CoroutineContext = Dispatchers.IO) {

    suspend fun <T> executeRequest(request: suspend () -> Response<T>): ApiResult<T> {
        return withContext(context) {
            try {
                val result = request.invoke()
                result.toApiResult()
            } catch (throwable: Throwable) {
                val exceptionConverted = getException(throwable)
                ApiResult(isSuccessful = false, throwable = exceptionConverted)
            }
        }
    }

    private fun getException(throwable: Throwable): Throwable {
        return when (throwable) {
            is HttpException -> {
                if (throwable.code() >= HttpURLConnection.HTTP_INTERNAL_ERROR)
                    Exception(throwable.message())
                else
                    Exception(throwable.message())
            }
            else -> throwable
        }
    }

    private fun <T> Response<T>.toApiResult(): ApiResult<T> {
        val headers = this.headers().toHashSet()
        val exception = if (!this.isSuccessful) getException(HttpException(this)) else null
        return ApiResult(
            body = this.body(),
            code = this.code(),
            isSuccessful = this.isSuccessful,
            headers = headers,
            throwable = exception
        )
    }
}
