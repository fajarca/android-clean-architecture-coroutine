package io.fajarca.home.di

import dagger.Component
import io.fajarca.core.di.CoreComponent
import io.fajarca.core.di.scope.FeatureScope
import io.fajarca.home.presentation.HomeFragment

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [
        FeatureModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface FeatureComponent {
    fun inject(homeFragment: HomeFragment)
}
