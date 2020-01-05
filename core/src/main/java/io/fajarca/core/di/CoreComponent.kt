package io.fajarca.core.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import io.fajarca.core.database.CharacterDao
import io.fajarca.core.di.modules.ContextModule
import io.fajarca.core.di.modules.DatabaseModule
import io.fajarca.core.di.modules.NetworkModule
import io.fajarca.core.di.modules.SharedPreferenceModule
import io.fajarca.core.network.service.ApiService
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
    fun apiService() : ApiService
    fun characterDao() : CharacterDao

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): CoreComponent
    }
}