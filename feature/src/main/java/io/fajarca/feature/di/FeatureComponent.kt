package io.fajarca.feature.di

import dagger.Component
import io.fajarca.core.di.CoreComponent

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

}