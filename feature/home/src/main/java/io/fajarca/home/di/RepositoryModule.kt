package io.fajarca.home.di

import dagger.Binds
import dagger.Module
import io.fajarca.home.data.repository.CharacterRepositoryImpl
import io.fajarca.home.domain.repository.CharacterRepository

@Module
interface RepositoryModule {

    @Binds
    fun bindProjectRepository(projectRepository: CharacterRepositoryImpl): CharacterRepository


}