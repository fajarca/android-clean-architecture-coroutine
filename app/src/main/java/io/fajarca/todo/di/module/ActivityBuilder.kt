package io.fajarca.todo.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.fajarca.todo.MainActivity

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}