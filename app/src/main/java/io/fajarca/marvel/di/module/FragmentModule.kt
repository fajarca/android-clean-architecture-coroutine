package io.fajarca.marvel.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.fajarca.marvel.ui.home.HomeFragment


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributesHomeFragment(): HomeFragment

}