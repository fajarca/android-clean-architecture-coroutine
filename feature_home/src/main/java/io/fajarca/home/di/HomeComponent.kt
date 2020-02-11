package io.fajarca.home.di

import dagger.Component
import io.fajarca.core.di.CoreComponent
import io.fajarca.core.di.scope.FeatureScope
import io.fajarca.home.presentation.screen.HomeFragment
import io.fajarca.home.presentation.screen.NewsFragment

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [
        HomeModule::class,
        RepositoryModule::class
    ]
)
interface HomeComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(newsFragment: NewsFragment)
}
