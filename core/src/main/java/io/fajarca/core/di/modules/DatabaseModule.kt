package io.fajarca.core.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.fajarca.core.Constant
import io.fajarca.core.database.MarvelDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideCharacterDao(db: MarvelDatabase) = db.characterDao()

    @Provides
    @Singleton
    fun provideDatabase(context: Context) = Room.databaseBuilder(context, MarvelDatabase::class.java, Constant.DATABASE_NAME)
        .build()
}