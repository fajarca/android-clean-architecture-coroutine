package io.fajarca.core.di.modules

import dagger.Binds
import dagger.Module
import io.fajarca.core.dispatcher.CoroutineDispatcherProvider
import io.fajarca.core.dispatcher.DispatcherProvider

@Module
interface CoroutineDispatcherModule {
    @Binds
    fun bindDispatcher(dispatcherProvider: CoroutineDispatcherProvider) : DispatcherProvider
}