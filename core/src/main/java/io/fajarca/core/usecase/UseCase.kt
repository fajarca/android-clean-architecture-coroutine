package io.fajarca.core.usecase

abstract class UseCase<out T> {
    abstract suspend fun execute(onSuccess : (data : T) -> Unit, onError : (throwable : Throwable) -> Unit): T
}