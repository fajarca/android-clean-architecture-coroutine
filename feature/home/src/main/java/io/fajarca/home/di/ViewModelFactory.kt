package io.fajarca.home.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.fajarca.core.di.scope.FeatureScope
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@FeatureScope
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}
