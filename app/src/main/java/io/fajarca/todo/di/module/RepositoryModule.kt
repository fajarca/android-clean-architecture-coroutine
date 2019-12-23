package io.fajarca.todo.di.module

import dagger.Module
import dagger.Provides
import io.fajarca.todo.data.mapper.CharactersMapper
import io.fajarca.todo.data.source.local.MarvelDatabase
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCharacterDao(db: MarvelDatabase) = db.characterDao()

    @Provides
    fun provideMapper() = CharactersMapper()
}