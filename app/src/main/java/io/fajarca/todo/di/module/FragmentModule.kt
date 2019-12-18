package io.fajarca.todo.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.fajarca.todo.ui.home.HomeFragment


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributesHomeFragment(): HomeFragment

}