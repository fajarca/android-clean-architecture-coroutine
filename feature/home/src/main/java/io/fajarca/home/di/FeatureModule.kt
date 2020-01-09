package io.fajarca.home.di

import dagger.Module
import dagger.Provides
import io.fajarca.core.database.CharacterDao
import io.fajarca.core.database.MarvelDatabase
import io.fajarca.core.di.scope.FeatureScope
import io.fajarca.home.data.ApiService
import io.fajarca.home.data.CharactersMapper
import io.fajarca.home.domain.repository.CharacterRepository
import io.fajarca.home.domain.usecase.GetCharactersUseCase
import retrofit2.Retrofit


@Module
class FeatureModule {

    @Provides
    @FeatureScope
    fun provideCharacterDao(db: MarvelDatabase) : CharacterDao = db.characterDao()

    @Provides
    @FeatureScope
    fun provideMapper() : CharactersMapper = CharactersMapper()

    @Provides
    @FeatureScope
    fun provideUseCase(repository: CharacterRepository) : GetCharactersUseCase = GetCharactersUseCase(repository)


    @Provides
    @FeatureScope
    fun provideApiService(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)
}