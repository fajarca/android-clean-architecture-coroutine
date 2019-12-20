package io.fajarca.todo.domain.model.common

sealed class Result<out T> {
    class Loading<out T>() : Result<T>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error<out T>(val cause: HttpResult) : Result<T>()

    companion object {
        fun <T> loading(): Result<T> {
            return Loading()
        }

        fun <T> success(data: T): Result<T> {
            return Success(data)
        }

        fun <T> error(cause: HttpResult): Result<T> {
            return Error(cause)
        }
    }
}