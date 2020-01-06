package io.fajarca.feature.di

import dagger.Component
import io.fajarca.core.di.CoreComponent
import io.fajarca.feature.ui.home.HomeFragment

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
