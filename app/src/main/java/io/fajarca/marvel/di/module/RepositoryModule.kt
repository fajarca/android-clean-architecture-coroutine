package io.fajarca.marvel.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.fajarca.marvel.data.mapper.CharactersMapper
import io.fajarca.marvel.data.repository.CharacterRepositoryImpl
import io.fajarca.marvel.data.source.local.MarvelDatabase
import io.fajarca.marvel.domain.repository.CharacterRepository
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindProjectRepository(projectRepository: CharacterRepositoryImpl): CharacterRepository


}