package io.fajarca.news.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.fajarca.core.di.ViewModelFactory
import io.fajarca.core.di.ViewModelKey
import io.fajarca.news.presentation.viewmodel.HomeViewModel

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun providesHomeViewModel(viewModel: HomeViewModel): ViewModel
}