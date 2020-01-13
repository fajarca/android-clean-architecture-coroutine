package io.fajarca.feature.di

import dagger.Component
import io.fajarca.core.di.CoreComponent
import io.fajarca.core.di.scope.FeatureScope
import io.fajarca.feature.presentation.CharacterDetailFragment

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [
        FeatureModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface CharacterDetailFeatureComponent {
    fun inject(homeFragment: CharacterDetailFragment)
}
