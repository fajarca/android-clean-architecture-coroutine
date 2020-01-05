package io.fajarca.marvel.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.fajarca.feature.ui.MainActivity

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}