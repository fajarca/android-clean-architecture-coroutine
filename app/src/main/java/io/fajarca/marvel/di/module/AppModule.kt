package io.fajarca.marvel.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.fajarca.marvel.core.Constant
import io.fajarca.marvel.core.MarvelApp
import io.fajarca.marvel.data.source.local.MarvelDatabase
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
    fun provideDatabase(context: Context) = Room.databaseBuilder(context, MarvelDatabase::class.java, Constant.DATABASE_NAME).build()

    @Provides
    @Singleton
    fun providesPreference(app: MarvelApp): SharedPreferences = app.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providesSharedPreference(sharedPreferences: SharedPreferences): SharedPreferences.Editor = sharedPreferences.edit()
}