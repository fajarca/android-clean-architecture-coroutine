package io.fajarca.feature.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.fajarca.core.di.scope.AppScope
import io.fajarca.feature.ui.home.HomeFragment


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributesHomeFragment(): HomeFragment

}