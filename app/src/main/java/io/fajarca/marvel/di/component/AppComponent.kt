package io.fajarca.marvel.di.component

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import io.fajarca.marvel.core.MarvelApp
import io.fajarca.marvel.di.builder.ActivityBuilder
import io.fajarca.marvel.di.module.*
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
    NetworkModule::class,
    DataModule::class,
    RepositoryModule::class,
    AndroidInjectionModule::class,
    ActivityBuilder::class,
    FragmentModule::class,
    ViewModelModule::class])

interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MarvelApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: MarvelApp)
}
