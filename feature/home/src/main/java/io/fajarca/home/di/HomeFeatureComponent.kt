package io.fajarca.home.di

import dagger.Component
import io.fajarca.core.di.CoreComponent
import io.fajarca.core.di.scope.FeatureScope
import io.fajarca.home.ui.home.HomeFragment

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [
        HomeFeatureModule::class,
        HomeRepositoryModule::class
    ]
)
interface HomeFeatureComponent {
    fun inject(homeFragment: HomeFragment)
}
