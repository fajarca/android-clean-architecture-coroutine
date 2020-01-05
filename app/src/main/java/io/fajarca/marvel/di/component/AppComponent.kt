package io.fajarca.marvel.di.component

import dagger.Component
import dagger.android.AndroidInjectionModule
import io.fajarca.core.di.CoreComponent
import io.fajarca.core.di.scope.AppScope
import io.fajarca.marvel.MarvelApp
import io.fajarca.marvel.di.builder.ActivityBuilder
import io.fajarca.marvel.di.module.DataModule
import io.fajarca.marvel.di.module.FragmentModule
import io.fajarca.marvel.di.module.RepositoryModule
import io.fajarca.marvel.di.module.ViewModelModule

@AppScope
@Component(
    dependencies = [CoreComponent::class] ,
    modules = [
    DataModule::class,
    RepositoryModule::class,
    AndroidInjectionModule::class,
    ActivityBuilder::class,
    FragmentModule::class,
    ViewModelModule::class]
)

interface AppComponent {

    @Component.Builder
    interface Builder {

        fun coreComponent(coreComponent: CoreComponent)
        fun build(): AppComponent
    }

    fun inject(app: MarvelApp)
}
