package io.fajarca.todo.domain.model.common

sealed class Result<out T> {
    class Loading<out T> : Result<T>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error<out T>(val cause: HttpResult) : Result<T>()
}