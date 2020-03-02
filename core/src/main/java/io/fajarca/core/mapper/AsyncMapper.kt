package io.fajarca.core.mapper

import kotlinx.coroutines.CoroutineDispatcher

abstract class AsyncMapper<in I, out O>{
    abstract suspend fun map(dispatcher: CoroutineDispatcher, input : I) :  O
}