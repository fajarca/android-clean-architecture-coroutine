package io.fajarca.core.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule {
    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app
}