package io.fajarca.characters.di

import dagger.Module
import dagger.Provides
import io.fajarca.core.database.CharacterDao
import io.fajarca.core.database.MarvelDatabase
import io.fajarca.core.di.scope.FeatureScope
import io.fajarca.characters.data.CharacterService
import io.fajarca.characters.data.mapper.CharacterDetailMapper
import io.fajarca.characters.data.mapper.CharactersMapper
import io.fajarca.characters.domain.repository.CharacterRepository
import io.fajarca.characters.domain.usecase.GetCharactersDetailUseCase
import io.fajarca.characters.domain.usecase.GetCharactersUseCase
import io.fajarca.characters.presentation.detail.CharacterDetailViewModel
import io.fajarca.characters.presentation.list.CharactersViewModel
import retrofit2.Retrofit


@Module
class CharacterModule {

    @Provides
    @FeatureScope
    fun provideCharacterDao(db: MarvelDatabase) : CharacterDao = db.characterDao()

    @Provides
    @FeatureScope
    fun provideMapper() : CharactersMapper =
        CharactersMapper()

    @Provides
    @FeatureScope
    fun provideDetailMapper() : CharacterDetailMapper =
        CharacterDetailMapper()

    @Provides
    @FeatureScope
    fun provideCharacterService(retrofit: Retrofit) : CharacterService = retrofit.create(CharacterService::class.java)

    @Provides
    @FeatureScope
    fun provideCharacterUseCase(repository: CharacterRepository) : GetCharactersUseCase = GetCharactersUseCase(repository)

    @Provides
    @FeatureScope
    fun provideCharacterDetailUseCase(repository: CharacterRepository) : GetCharactersDetailUseCase = GetCharactersDetailUseCase(repository)

    @Provides
    @FeatureScope
    fun provideCharacterDetailsViewModelFactory(
        useCase: GetCharactersDetailUseCase
    ) = CharacterDetailViewModel.Factory(useCase)

    @Provides
    @FeatureScope
    fun provideCharacterListViewModelFactory(
        useCase: GetCharactersUseCase
    ) = CharactersViewModel.Factory(useCase)
}