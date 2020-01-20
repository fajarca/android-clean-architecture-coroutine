package io.fajarca.characters.di

import dagger.Binds
import dagger.Module
import io.fajarca.characters.data.CharacterRepositoryImpl
import io.fajarca.characters.domain.repository.CharacterRepository

@Module
interface RepositoryModule {
    @Binds
    fun bindProjectRepository(projectRepository: CharacterRepositoryImpl): CharacterRepository
}