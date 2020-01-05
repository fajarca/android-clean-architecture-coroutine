package io.fajarca.marvel.di.module

import dagger.Module
import dagger.Provides
import io.fajarca.marvel.data.mapper.CharactersMapper

@Module
class DataModule {

    @Provides
    fun provideMapper() = CharactersMapper()

}