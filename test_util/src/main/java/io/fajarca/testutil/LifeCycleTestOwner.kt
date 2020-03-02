package io.fajarca.testutil

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/**
 * We’ll use this to replicate the presence of an activity
 * or fragment in our test class.
 */
class LifeCycleTestOwner : LifecycleOwner {

    private val registry = LifecycleRegistry(this)

    override fun getLifecycle(): Lifecycle {
        return registry
    }

    fun onCreate() {
        registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    fun onResume() {
        registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    fun onDestroy() {
        registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}