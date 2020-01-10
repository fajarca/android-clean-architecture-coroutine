package io.fajarca.core.common

abstract class UseCase<out T> {
    abstract suspend fun execute(onSuccess : (data : T) -> Unit, onError : (throwable : Throwable) -> Unit)
}