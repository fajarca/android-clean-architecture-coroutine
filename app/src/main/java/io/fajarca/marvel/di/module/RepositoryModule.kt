package io.fajarca.marvel.di.module

import dagger.Binds
import dagger.Module
import io.fajarca.feature.data.repository.CharacterRepositoryImpl
import io.fajarca.feature.domain.repository.CharacterRepository

@Module
interface RepositoryModule {

    @Binds
    fun bindProjectRepository(projectRepository: CharacterRepositoryImpl): CharacterRepository


}