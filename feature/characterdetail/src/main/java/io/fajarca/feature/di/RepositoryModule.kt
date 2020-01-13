package io.fajarca.feature.di

import dagger.Binds
import dagger.Module
import io.fajarca.feature.data.CharacterDetailRepositoryImpl
import io.fajarca.feature.domain.repository.CharacterDetailRepository

@Module
interface RepositoryModule {
    @Binds
    fun bindRepository(repository: CharacterDetailRepositoryImpl): CharacterDetailRepository
}