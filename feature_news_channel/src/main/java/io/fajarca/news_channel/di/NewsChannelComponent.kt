package io.fajarca.news_channel.di

import dagger.Component
import io.fajarca.core.di.CoreComponent
import io.fajarca.core.di.scope.FeatureScope
import io.fajarca.news_channel.presentation.NewsChannelFragment

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [
        NewsChannelModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface NewsChannelComponent {
    fun inject(homeFragment: NewsChannelFragment)
}
