package io.fajarca.marvel

import android.app.Application
import android.content.Context
import io.fajarca.core.di.CoreComponent
import io.fajarca.core.di.DaggerCoreComponent
import io.fajarca.marvel.di.component.DaggerAppComponent
import timber.log.Timber

class MarvelApp : Application() {



    lateinit var coreComponent: CoreComponent

    companion object {

        val core by lazy {

        }
        /**
         * Obtain core dagger component.
         *
         * @param context The application context
         */
        @JvmStatic
        fun coreComponent(context: Context)= (context.applicationContext as? MarvelApp)?.coreComponent
    }

    override fun onCreate() {
        super.onCreate()

        initCoreDependencyInjection()
        initAppDependencyInjection()


        initTimber()
    }



    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    /**
     * Initialize core dependency injection component.
     */
    private fun initCoreDependencyInjection() {
        coreComponent = DaggerCoreComponent
            .builder()
            .application(this)
            .build()
    }

    private fun initAppDependencyInjection() {
        DaggerAppComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }
}
