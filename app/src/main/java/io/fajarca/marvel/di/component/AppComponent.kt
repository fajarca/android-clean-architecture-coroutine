package io.fajarca.marvel.di.component

import dagger.Component
import dagger.android.AndroidInjectionModule
import io.fajarca.core.di.CoreComponent
import io.fajarca.core.di.scope.AppScope
import io.fajarca.marvel.MarvelApp
import io.fajarca.marvel.di.builder.ActivityBuilder

@AppScope
@Component(
    dependencies = [CoreComponent::class] ,
    modules = [
    AndroidInjectionModule::class,
    ActivityBuilder::class]
)

interface AppComponent {

    @Component.Builder
    interface Builder {

        fun coreComponent(coreComponent: CoreComponent)
        fun build(): AppComponent
    }

    fun inject(app: MarvelApp)
}
