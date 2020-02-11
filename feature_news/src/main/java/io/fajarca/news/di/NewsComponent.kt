package io.fajarca.news.di

import dagger.Component
import io.fajarca.core.di.CoreComponent
import io.fajarca.core.di.scope.FeatureScope
import io.fajarca.news.presentation.screen.HomeFragment
import io.fajarca.news.presentation.screen.NewsFragment

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [
        NewsModule::class,
        RepositoryModule::class
    ]
)
interface NewsComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(newsFragment: NewsFragment)
}
