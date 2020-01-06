package io.fajarca.feature.di

import dagger.Module
import dagger.Provides
import io.fajarca.core.database.MarvelDatabase
import io.fajarca.feature.data.mapper.CharactersMapper


@Module
class FeatureModule {

    @Provides
    @FeatureScope
    fun provideCharacterDao(db: MarvelDatabase) = db.characterDao()

    @Provides
    @FeatureScope
    fun provideMapper() = CharactersMapper()

}