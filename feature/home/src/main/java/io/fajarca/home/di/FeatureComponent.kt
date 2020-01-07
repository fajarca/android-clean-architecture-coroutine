package io.fajarca.home.di

import dagger.Component
import io.fajarca.core.di.CoreComponent
import io.fajarca.home.ui.home.HomeFragment

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [
        FeatureModule::class,
        RepositoryModule::class
    ]
)
interface FeatureComponent {
    fun inject(homeFragment: HomeFragment)
}
