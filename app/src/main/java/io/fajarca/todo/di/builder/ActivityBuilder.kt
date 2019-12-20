package io.fajarca.todo.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.fajarca.todo.ui.MainActivity

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}