package io.fajarca.marvel.di.module

import dagger.Module
import dagger.Provides
import io.fajarca.marvel.data.mapper.CharactersMapper
import io.fajarca.marvel.data.source.local.MarvelDatabase
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCharacterDao(db: MarvelDatabase) = db.characterDao()

    @Provides
    fun provideMapper() = CharactersMapper()
}