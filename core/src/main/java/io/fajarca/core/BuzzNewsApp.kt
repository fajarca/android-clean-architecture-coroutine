package io.fajarca.core

import android.app.Application
import android.content.Context
import io.fajarca.core.di.CoreComponent
import io.fajarca.core.di.DaggerCoreComponent
import timber.log.Timber

class BuzzNewsApp : Application() {



    lateinit var coreComponent: CoreComponent

    companion object {

        /**
         * Obtain core dagger component.
         *
         * @param context The application context
         */
        @JvmStatic
        fun coreComponent(context: Context)= (context.applicationContext as? BuzzNewsApp)?.coreComponent
    }

    override fun onCreate() {
        super.onCreate()

        initCoreDependencyInjection()
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

}
