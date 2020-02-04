package io.fajarca.news_channel.di

import dagger.Module
import dagger.Provides
import io.fajarca.core.database.NewsDatabase
import io.fajarca.core.database.dao.NewsChannelDao
import io.fajarca.core.di.scope.FeatureScope
import io.fajarca.news_channel.data.ChannelService
import io.fajarca.news_channel.data.mapper.NewsChannelMapper
import io.fajarca.news_channel.data.source.NewsChannelRemoteDataSource
import io.fajarca.news_channel.domain.usecase.GetNewsChannelUseCase
import io.fajarca.news_channel.presentation.NewsChannelViewModel
import retrofit2.Retrofit


@Module
class NewsChannelModule {

    @Provides
    @FeatureScope
    fun provideNewsChannelDao(db: NewsDatabase) : NewsChannelDao = db.newsChannelDao()

    @Provides
    @FeatureScope
    fun provideMapper() : NewsChannelMapper = NewsChannelMapper()

    @Provides
    @FeatureScope
    fun provideNewsChannelService(retrofit: Retrofit) : ChannelService = retrofit.create(ChannelService::class.java)

    @Provides
    @FeatureScope
    fun provideRemoteDataSource(channelService: ChannelService) = NewsChannelRemoteDataSource(channelService)

    @Provides
    @FeatureScope
    fun provideNewsChannelViewModelFactory(getNewsChannelUseCase: GetNewsChannelUseCase) = NewsChannelViewModel.Factory(getNewsChannelUseCase)
}