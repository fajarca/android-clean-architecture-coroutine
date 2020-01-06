package io.fajarca.core.network

import com.squareup.moshi.Moshi
import io.fajarca.core.common.HttpResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import io.fajarca.core.common.Result

open class RemoteDataSource {
    open suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): Result<T> {
        return withContext(dispatcher) {
            try {
                Result.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        val result = when(throwable.code()) {
                            401 -> Result.Error(HttpResult.UNAUTHORIZED)
                            in 400..420 -> parseHttpError(throwable)
                            in 500..599 -> Result.Error(HttpResult.SERVER_ERROR)
                            else -> Result.Error(HttpResult.NOT_DEFINED)
                        }
                        result
                    }
                    is UnknownHostException -> Result.Error(HttpResult.NO_CONNECTION)
                    is SocketTimeoutException -> Result.Error(HttpResult.TIMEOUT)
                    is IOException -> Result.Error(HttpResult.BAD_RESPONSE)
                    else -> Result.Error(HttpResult.NOT_DEFINED)

                }
            }
        }
    }

    private fun parseHttpError(throwable: HttpException) : Result<Nothing> {
        return try {
            val errorBody = throwable.response()?.errorBody()?.string() ?: "Unknown HTTP error"
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(JSONObject::class.java)
            val errorMessage = adapter.fromJson(errorBody)
            Result.Error(HttpResult.CLIENT_ERROR, throwable.code(), errorMessage.toString())
        } catch (exception : Exception) {
            Result.Error(HttpResult.CLIENT_ERROR)
        }
    }
}