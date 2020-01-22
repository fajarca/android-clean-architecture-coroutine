package io.fajarca.characters.di

import dagger.Module
import dagger.Provides
import io.fajarca.characters.data.CharacterService
import io.fajarca.characters.data.mapper.CharacterDetailMapper
import io.fajarca.characters.data.mapper.CharacterSeriesMapper
import io.fajarca.characters.data.mapper.CharactersMapper
import io.fajarca.characters.data.source.CharacterRemoteDataSource
import io.fajarca.characters.domain.repository.CharacterRepository
import io.fajarca.characters.domain.usecase.GetCharactersDetailUseCase
import io.fajarca.characters.domain.usecase.GetCharactersSeriesUseCase
import io.fajarca.characters.domain.usecase.GetCharactersUseCase
import io.fajarca.characters.presentation.detail.CharacterDetailViewModel
import io.fajarca.characters.presentation.list.CharactersViewModel
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
    fun provideDetailMapper() : CharacterDetailMapper = CharacterDetailMapper()

    @Provides
    @FeatureScope
    fun provideCharacterSeriesMapper() : CharacterSeriesMapper = CharacterSeriesMapper()

    @Provides
    @FeatureScope
    fun provideCharacterService(retrofit: Retrofit) : CharacterService = retrofit.create(CharacterService::class.java)

    @Provides
    @FeatureScope
    fun provideRemoteDataSource(characterService: CharacterService) = CharacterRemoteDataSource(characterService)

    @Provides
    @FeatureScope
    fun provideGetCharactersSeriesUseCase(repository: CharacterRepository) = GetCharactersSeriesUseCase(repository)

    @Provides
    @FeatureScope
    fun provideGetCharactersDetailUseCase(repository: CharacterRepository) = GetCharactersDetailUseCase(repository)

    @Provides
    @FeatureScope
    fun provideGetCharactersUseCase(repository: CharacterRepository) = GetCharactersUseCase(repository)

    @Provides
    @FeatureScope
    fun provideCharacterDetailsViewModelFactory(
        useCase: GetCharactersDetailUseCase,
        getCharactersSeriesUseCase: GetCharactersSeriesUseCase
    ) = CharacterDetailViewModel.Factory(useCase, getCharactersSeriesUseCase)

    @Provides
    @FeatureScope
    fun provideCharacterListViewModelFactory(
        useCase: GetCharactersUseCase
    ) = CharactersViewModel.Factory(useCase)
}