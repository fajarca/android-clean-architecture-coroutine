package io.fajarca.todo.di.module

import dagger.Module
import dagger.Provides
import io.fajarca.todo.data.mapper.CharactersMapper
import io.fajarca.todo.data.source.local.TodoDatabase
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCastDao(db: TodoDatabase) = db.todoDao()

    @Provides
    fun provideMapper() = CharactersMapper()
}