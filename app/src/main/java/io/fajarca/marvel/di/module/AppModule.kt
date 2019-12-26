package io.fajarca.marvel.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.fajarca.marvel.core.MarvelApp
import io.fajarca.marvel.data.source.local.MarvelDatabase
import io.fajarca.marvel.data.service.ApiService
import io.fajarca.marvel.core.DATABASE_NAME
import io.fajarca.marvel.core.PREF_NAME
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: MarvelApp): Context = app

    @Provides
    @Singleton
    fun provideApplications(app: MarvelApp): Application = app

    @Provides
    @Singleton
    fun provideDatabase(context: Context) = Room.databaseBuilder(context, MarvelDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesPreference(app: MarvelApp): SharedPreferences = app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providesSharedPreference(sharedPreferences: SharedPreferences): SharedPreferences.Editor = sharedPreferences.edit()
}