package io.fajarca.characters.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.fajarca.characters.presentation.detail.CharacterDetailViewModel
import io.fajarca.core.di.ViewModelFactory
import io.fajarca.core.di.ViewModelKey
import io.fajarca.characters.presentation.list.CharactersViewModel

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    internal abstract fun provideCharacterListViewModel(viewModel: CharactersViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailViewModel::class)
    internal abstract fun provideCharacterDetailViewModel(viewModel: CharacterDetailViewModel): ViewModel
}