package io.fajarca.characters.di

import dagger.Component
import io.fajarca.core.di.CoreComponent
import io.fajarca.core.di.scope.FeatureScope
import io.fajarca.characters.presentation.list.CharactersFragment

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [
        CharacterModule::class,
        RepositoryModule::class
    ]
)
interface CharacterListComponent {
    fun inject(homeFragment: CharactersFragment)
}
