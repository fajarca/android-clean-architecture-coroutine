package io.fajarca.home.di

import dagger.Module
import dagger.Provides
import io.fajarca.home.data.NewsService
import io.fajarca.home.data.mapper.NewsMapper
import io.fajarca.home.data.source.NewsRemoteDataSource
import io.fajarca.home.domain.repository.NewsRepository
import io.fajarca.home.domain.usecase.GetTopHeadlinesUseCase
import io.fajarca.home.presentation.HomeViewModel
import io.fajarca.core.database.TopHeadlineDao
import io.fajarca.core.database.NewsDatabase
import io.fajarca.core.di.scope.FeatureScope
import io.fajarca.home.domain.usecase.GetNewsUseCase
import io.fajarca.home.presentation.mapper.NewsPresentationMapper
import retrofit2.Retrofit


@Module
class CharacterModule {

    @Provides
    @FeatureScope
    fun provideTopHeadlineDao(db: NewsDatabase) : TopHeadlineDao = db.topHeadlineDao()

    @Provides
    @FeatureScope
    fun provideMapper() : NewsMapper = NewsMapper()

    @Provides
    @FeatureScope
    fun provideCharacterService(retrofit: Retrofit) : NewsService = retrofit.create(NewsService::class.java)

    @Provides
    @FeatureScope
    fun provideRemoteDataSource(characterService: NewsService) = NewsRemoteDataSource(characterService)

    @Provides
    @FeatureScope
    fun provideGetCharactersUseCase(repository: NewsRepository) = GetTopHeadlinesUseCase(repository)

    @Provides
    @FeatureScope
    fun provideCharacterListViewModelFactory(
        useCase: GetTopHeadlinesUseCase,
        getNewsUseCase: GetNewsUseCase,
        mapper : NewsPresentationMapper
    ) = HomeViewModel.Factory(useCase, getNewsUseCase, mapper)
}