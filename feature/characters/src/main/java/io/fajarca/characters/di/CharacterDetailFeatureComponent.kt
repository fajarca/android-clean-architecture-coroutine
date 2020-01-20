package io.fajarca.characters.di

import dagger.Component
import io.fajarca.core.di.CoreComponent
import io.fajarca.core.di.scope.FeatureScope
import io.fajarca.characters.presentation.detail.CharacterDetailFragment

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
