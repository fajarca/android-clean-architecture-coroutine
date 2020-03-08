package io.fajarca.news_channel.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.fajarca.core.di.ViewModelFactory
import io.fajarca.core.di.ViewModelKey
import io.fajarca.news_channel.presentation.NewsChannelViewModel

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(NewsChannelViewModel::class)
    abstract fun providesNewsChannelViewModel(viewModel: NewsChannelViewModel): ViewModel
}