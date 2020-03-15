package io.fajarca.news.di

import dagger.Binds
import dagger.Module
import io.fajarca.core.dispatcher.CoroutineDispatcherProvider
import io.fajarca.core.dispatcher.DispatcherProvider
import io.fajarca.news.data.NewsRepositoryImpl
import io.fajarca.news.domain.repository.NewsRepository

@Module
interface RepositoryModule {
    @Binds
    fun bindRepository(repository: NewsRepositoryImpl): NewsRepository
}