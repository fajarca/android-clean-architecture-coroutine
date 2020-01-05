package io.fajarca.core.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.fajarca.core.Constant
import javax.inject.Singleton


@Module
class SharedPreferenceModule {

    @Provides
    @Singleton
    fun providesPreference(context: Context): SharedPreferences = context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providesSharedPreference(sharedPreferences: SharedPreferences): SharedPreferences.Editor = sharedPreferences.edit()
}