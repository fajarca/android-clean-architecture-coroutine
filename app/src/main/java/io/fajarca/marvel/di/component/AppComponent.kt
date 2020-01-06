package io.fajarca.marvel.di.component

import dagger.Component
import io.fajarca.core.di.CoreComponent
import io.fajarca.core.di.scope.AppScope
import io.fajarca.core.MarvelApp

@AppScope
@Component(
    dependencies = [CoreComponent::class]
)

interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun coreComponent(coreComponent: CoreComponent) : Builder
    }

    fun inject(app: MarvelApp)
}
