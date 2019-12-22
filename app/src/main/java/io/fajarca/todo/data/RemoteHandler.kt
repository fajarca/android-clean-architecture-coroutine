package io.fajarca.todo.data

import io.fajarca.todo.domain.model.common.HttpResult
import io.fajarca.todo.domain.model.common.Result
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


open class RemoteHandler {

    suspend fun <T> getApiResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    return Result.success(body)
                }
            }
            return Result.error(map(response.code()))
        } catch (e: Exception) {
            return Result.error(map(e))
        }
    }


     fun map(throwable: Throwable): HttpResult {
        when (throwable) {
            is HttpException -> {
                if (throwable.code() == 401) {
                    return HttpResult.UNAUTHORIZED
                } else if (throwable.code() >= 500) {
                    return HttpResult.SERVER_ERROR
                }

                return HttpResult.NOT_DEFINED
            }

            is SocketTimeoutException -> {
                return HttpResult.TIMEOUT
            }

            is IOException -> {
                return HttpResult.NO_CONNECTION
            }

            is UnknownHostException -> {
                return HttpResult.NO_CONNECTION
            }

            else -> return HttpResult.NOT_DEFINED
        }
    }

     fun map(code: Int): HttpResult {
        return when (code) {
            in 200..226 -> HttpResult.OK
            401 -> HttpResult.UNAUTHORIZED
            in 400..420 -> HttpResult.CLIENT_ERROR
            in 500..599 -> HttpResult.SERVER_ERROR
            else -> return HttpResult.NOT_DEFINED
        }
    }
}