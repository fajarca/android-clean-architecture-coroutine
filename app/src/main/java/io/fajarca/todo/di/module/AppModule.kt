package io.fajarca.todo.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.fajarca.todo.core.TodoApp
import io.fajarca.todo.data.source.local.TodoDatabase
import io.fajarca.todo.data.service.ApiService
import io.fajarca.todo.core.DATABASE_NAME
import io.fajarca.todo.core.PREF_NAME
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: TodoApp): Context = app

    @Provides
    @Singleton
    fun provideApplications(app: TodoApp): Application = app

    @Provides
    @Singleton
    fun provideDatabase(context: Context) = Room.databaseBuilder(context, TodoDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesPreference(app: TodoApp): SharedPreferences = app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providesSharedPreference(sharedPreferences: SharedPreferences): SharedPreferences.Editor = sharedPreferences.edit()

}