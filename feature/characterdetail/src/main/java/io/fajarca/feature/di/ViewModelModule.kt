package io.fajarca.feature.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.fajarca.core.di.ViewModelFactory
import io.fajarca.core.di.ViewModelKey
import io.fajarca.feature.presentation.CharacterDetailViewModel

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailViewModel::class)
    internal abstract fun provideCharacterDetailViewModel(viewModel: CharacterDetailViewModel): ViewModel

}