package io.fajarca.core.network

import com.squareup.moshi.Moshi
import io.fajarca.core.vo.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class RemoteDataSource {
    open suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): Result<T> {
        return withContext(dispatcher) {
            try {
                Result.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        val result = when(throwable.code()) {
                            in 400..451 -> parseHttpError(throwable)
                            in 500..599 -> error(HttpResult.SERVER_ERROR, throwable.code(),"Server error")
                            else -> error(HttpResult.NOT_DEFINED, throwable.code(), "Undefined error")
                        }
                        result
                    }
                    is UnknownHostException -> error(HttpResult.NO_CONNECTION, null, "No internet connection")
                    is SocketTimeoutException -> error(HttpResult.TIMEOUT,null,  "Slow connection")
                    is IOException -> error(HttpResult.BAD_RESPONSE, null, throwable.message)
                    else -> error(HttpResult.NOT_DEFINED, null, throwable.message)

                }
            }
        }
    }

    private fun error(cause : HttpResult, code : Int?, errorMessage : String?) : Result.Error {
        return Result.Error(cause, code,  errorMessage)
    }

    private fun parseHttpError(throwable: HttpException) : Result<Nothing> {
        return try {
            val errorBody = throwable.response()?.errorBody()?.string() ?: "Unknown HTTP error body"
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(Object::class.java)
            val errorMessage = adapter.fromJson(errorBody)
            error(HttpResult.CLIENT_ERROR, throwable.code(), errorMessage.toString())
        } catch (exception : Exception) {
            error(HttpResult.CLIENT_ERROR, throwable.code(), exception.localizedMessage)
        }
    }
}