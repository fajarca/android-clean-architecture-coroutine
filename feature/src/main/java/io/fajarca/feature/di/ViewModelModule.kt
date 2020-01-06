package io.fajarca.feature.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.fajarca.feature.ui.home.HomeViewModel
import io.fajarca.core.common.ViewModelFactory
import io.fajarca.core.di.qualifier.ViewModelKey

@Module
abstract class ViewModelModule {

    @Binds
     abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
     abstract fun providesHomeViewModel(viewModel: HomeViewModel): ViewModel


}