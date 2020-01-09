package io.fajarca.home.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.fajarca.core.database.CharacterDao
import io.fajarca.core.database.MarvelDatabase
import io.fajarca.core.di.scope.FeatureScope
import io.fajarca.home.data.CharacterRepositoryImpl
import io.fajarca.home.data.CharactersMapper
import io.fajarca.home.domain.repository.CharacterRepository

@Module
interface RepositoryModule {
    @Binds
    fun bindProjectRepository(projectRepository: CharacterRepositoryImpl): CharacterRepository
}