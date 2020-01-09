package io.fajarca.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import io.fajarca.core.database.MarvelDatabase
import io.fajarca.core.di.modules.*
import retrofit2.Retrofit
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        SharedPreferenceModule::class
    ]
)
interface CoreComponent {
    fun context() : Context
    fun sharedPreference() : SharedPreferences
    fun sharedPreferenceEditor() : SharedPreferences.Editor
    fun marvelDatabase() : MarvelDatabase
    fun retrofit() : Retrofit


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): CoreComponent
    }
}