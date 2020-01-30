package io.fajarca.home.di

import dagger.Binds
import dagger.Module
import io.fajarca.home.data.NewsRepositoryImpl
import io.fajarca.home.domain.repository.NewsRepository

@Module
interface RepositoryModule {
    @Binds
    fun bindProjectRepository(projectRepository: NewsRepositoryImpl): NewsRepository
}