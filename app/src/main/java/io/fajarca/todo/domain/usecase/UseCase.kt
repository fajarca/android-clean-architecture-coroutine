package io.fajarca.todo.domain.usecase

import io.fajarca.todo.domain.model.common.HttpResult
import io.fajarca.todo.domain.model.common.Result
import io.fajarca.todo.ui.CoroutinesDispatcherProvider
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class UseCase {

    suspend fun <T> safeApiCall(dispatcher: CoroutinesDispatcherProvider, apiCall: suspend () -> T): Result<T> {
        return withContext(dispatcher.io) {
            try {
                Result.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> Result.Error<Nothing>(HttpResult.NO_CONNECTION)
                    is SocketTimeoutException -> Result.Error(HttpResult.TIMEOUT)
                    is UnknownHostException -> Result.Error(HttpResult.NO_CONNECTION)
                    is HttpException -> {

                        val code = throwable.code()
                        if (code == 401) {
                            Result.Error<HttpResult>(HttpResult.UNAUTHORIZED)
                        } else if (throwable.code() >= 500) {
                            Result.Error<HttpResult>(HttpResult.SERVER_ERROR)
                        }

                        Result.Error(HttpResult.NOT_DEFINED)
                    }
                    else -> {
                        Result.Error(HttpResult.NOT_DEFINED)
                    }
                }
            }
        }
    }


    private fun map(code: Int): HttpResult {
        return when (code) {
            in 200..226 -> HttpResult.OK
            401 -> HttpResult.UNAUTHORIZED
            in 400..420 -> HttpResult.CLIENT_ERROR
            in 500..599 -> HttpResult.SERVER_ERROR
            else -> return HttpResult.NOT_DEFINED
        }
    }

    private fun map(throwable: Throwable): HttpResult {
        when (throwable) {
            // if throwable is an instance of HttpException
            // then attempt to parse error data from response body
            is HttpException -> {
                // handle UNAUTHORIZED situation (when token expired)
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
}