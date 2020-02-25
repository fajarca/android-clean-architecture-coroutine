package io.fajarca.news.di

import dagger.Module
import dagger.Provides
import io.fajarca.news.data.NewsService
import io.fajarca.news.data.mapper.NewsMapper
import io.fajarca.news.data.source.NewsRemoteDataSource
import io.fajarca.news.presentation.viewmodel.HomeViewModel
import io.fajarca.core.database.dao.NewsDao
import io.fajarca.core.database.NewsDatabase
import io.fajarca.core.di.scope.FeatureScope
import io.fajarca.news.domain.usecase.GetNewsUseCase
import io.fajarca.news.domain.usecase.InsertNewsUseCase
import io.fajarca.news.presentation.mapper.NewsPresentationMapper
import retrofit2.Retrofit


@Module
class NewsModule {

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
        insertNewsUseCase: InsertNewsUseCase,
        mapper : NewsPresentationMapper
    ) = HomeViewModel.Factory(getNewsUseCase, insertNewsUseCase, mapper)
}