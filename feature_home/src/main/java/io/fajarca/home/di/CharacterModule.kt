package io.fajarca.home.di

import dagger.Module
import dagger.Provides
import io.fajarca.home.data.NewsService
import io.fajarca.home.data.mapper.CharactersMapper
import io.fajarca.home.data.source.NewsRemoteDataSource
import io.fajarca.home.domain.repository.CharacterRepository
import io.fajarca.home.domain.usecase.GetCharactersUseCase
import io.fajarca.home.presentation.list.CharactersViewModel
import io.fajarca.core.database.CharacterDao
import io.fajarca.core.database.MarvelDatabase
import io.fajarca.core.di.scope.FeatureScope
import retrofit2.Retrofit


@Module
class CharacterModule {

    @Provides
    @FeatureScope
    fun provideCharacterDao(db: MarvelDatabase) : CharacterDao = db.characterDao()

    @Provides
    @FeatureScope
    fun provideMapper() : CharactersMapper = CharactersMapper()

    @Provides
    @FeatureScope
    fun provideCharacterService(retrofit: Retrofit) : NewsService = retrofit.create(NewsService::class.java)

    @Provides
    @FeatureScope
    fun provideRemoteDataSource(characterService: NewsService) = NewsRemoteDataSource(characterService)

    @Provides
    @FeatureScope
    fun provideGetCharactersUseCase(repository: CharacterRepository) = GetCharactersUseCase(repository)

    @Provides
    @FeatureScope
    fun provideCharacterListViewModelFactory(
        useCase: GetCharactersUseCase
    ) = CharactersViewModel.Factory(useCase)
}