package io.fajarca.home.di

import dagger.Module
import dagger.Provides
import io.fajarca.home.data.NewsService
import io.fajarca.home.data.mapper.NewsMapper
import io.fajarca.home.data.source.NewsRemoteDataSource
import io.fajarca.home.presentation.HomeViewModel
import io.fajarca.core.database.dao.NewsDao
import io.fajarca.core.database.NewsDatabase
import io.fajarca.core.di.scope.FeatureScope
import io.fajarca.home.domain.usecase.GetNewsUseCase
import io.fajarca.home.presentation.mapper.NewsPresentationMapper
import retrofit2.Retrofit


@Module
class HomeModule {

    @Provides
    @FeatureScope
    fun provideNewseDao(db: NewsDatabase) : NewsDao = db.newsDao()

    @Provides
    @FeatureScope
    fun provideMapper() : NewsMapper = NewsMapper()

    @Provides
    @FeatureScope
    fun provideNewsService(retrofit: Retrofit) : NewsService = retrofit.create(NewsService::class.java)

    @Provides
    @FeatureScope
    fun provideRemoteDataSource(characterService: NewsService) = NewsRemoteDataSource(characterService)

    @Provides
    @FeatureScope
    fun provideCharacterListViewModelFactory(
        getNewsUseCase: GetNewsUseCase,
        mapper : NewsPresentationMapper
    ) = HomeViewModel.Factory(getNewsUseCase, mapper)
}