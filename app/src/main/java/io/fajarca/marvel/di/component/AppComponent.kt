package io.fajarca.marvel.di.component

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import io.fajarca.core.di.CoreComponent
import io.fajarca.core.di.scope.AppScope
import io.fajarca.feature.di.DaggerFeatureComponent
import io.fajarca.feature.di.ViewModelModule
import io.fajarca.marvel.MarvelApp
import io.fajarca.marvel.di.builder.ActivityBuilder

@AppScope
@Component(
    dependencies = [CoreComponent::class] ,
    modules = [AndroidSupportInjectionModule::class, ActivityBuilder::class, FragmentModule::class, ViewModelModule::class]
)

interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun coreComponent(coreComponent: CoreComponent) : Builder
    }

    fun inject(app: MarvelApp)
}
