package io.fajarca.feature.di

import dagger.Module
import dagger.Provides
import io.fajarca.core.database.CharacterDao
import io.fajarca.core.database.MarvelDatabase
import io.fajarca.core.di.scope.FeatureScope
import io.fajarca.feature.data.ApiService
import io.fajarca.feature.data.CharacterMapper
import io.fajarca.feature.domain.repository.CharacterDetailRepository
import io.fajarca.feature.domain.usecase.GetCharactersDetailUseCase
import retrofit2.Retrofit


@Module
class FeatureModule {

    @Provides
    @FeatureScope
    fun provideCharacterDao(db: MarvelDatabase) : CharacterDao = db.characterDao()

    @Provides
    @FeatureScope
    fun provideMapper() : CharacterMapper = CharacterMapper()

    @Provides
    @FeatureScope
    fun provideUseCase(repository: CharacterDetailRepository) : GetCharactersDetailUseCase = GetCharactersDetailUseCase(repository)


    @Provides
    @FeatureScope
    fun provideApiService(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)
}