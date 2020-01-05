package io.fajarca.marvel.di.module

import dagger.Binds
import dagger.Module
import io.fajarca.marvel.data.repository.CharacterRepositoryImpl
import io.fajarca.marvel.domain.repository.CharacterRepository

@Module
interface RepositoryModule {

    @Binds
    fun bindProjectRepository(projectRepository: CharacterRepositoryImpl): CharacterRepository


}