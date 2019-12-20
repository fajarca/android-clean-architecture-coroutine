package io.fajarca.todo.di.component

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import io.fajarca.todo.core.TodoApp
import io.fajarca.todo.di.builder.ActivityBuilder
import io.fajarca.todo.di.module.AppModule
import io.fajarca.todo.di.module.NetworkModule
import io.fajarca.todo.di.module.RepositoryModule
import io.fajarca.todo.di.module.FragmentModule
import io.fajarca.todo.di.module.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
    NetworkModule::class,
    RepositoryModule::class,
    AndroidInjectionModule::class,
    ActivityBuilder::class,
    FragmentModule::class,
    ViewModelModule::class])

interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: TodoApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: TodoApp)
}
