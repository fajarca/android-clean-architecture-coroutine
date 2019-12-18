package io.fajarca.todo.base

import retrofit2.Response
import io.fajarca.todo.vo.Result
import timber.log.Timber


open class BaseRemoteDataSource {

    suspend fun <T> getApiResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    return Result.success(body)
                }
            }
            return error(response.code(), "${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(-1, e.message ?: e.toString())
        }
    }

    private fun <T> error(httpCode : Int, message: String): Result<T> {
        Timber.e(message)
        return Result.error(httpCode,"Network call has failed for a following reason : $message", null)
    }
}