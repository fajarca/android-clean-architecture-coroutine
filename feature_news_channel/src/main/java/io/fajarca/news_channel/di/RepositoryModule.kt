package io.fajarca.news_channel.di

import dagger.Binds
import dagger.Module
import io.fajarca.news_channel.data.NewsChannelRepositoryImpl
import io.fajarca.news_channel.domain.repository.NewsChannelRepository

@Module
interface RepositoryModule {
    @Binds
    fun bindProjectRepository(projectRepository: NewsChannelRepositoryImpl): NewsChannelRepository
}