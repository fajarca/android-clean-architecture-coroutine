package io.fajarca.core.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.fajarca.core.vo.Constant
import io.fajarca.core.database.MarvelDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context) : MarvelDatabase = Room.databaseBuilder(context, MarvelDatabase::class.java, Constant.DATABASE_NAME)
        .build()
}