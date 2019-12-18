package io.fajarca.todo.util.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun String?.toLocalizedDatetimeFormat(): String {
    if (this.isNullOrEmpty()) return "-"

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    try {

        val date = dateFormat.parse(this)
        return DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return ""
}

/**
 * Creates a [Flow] containing values dispatched by originating [LiveData]: at the start
 * a flow collector receives the latest value held by LiveData and then observes LiveData updates.
 *
 * When a collection of the returned flow starts the originating [LiveData] becomes
 * [active][LiveData.onActive]. Similarly, when a collection completes [LiveData] becomes
 * [inactive][LiveData.onInactive].
 *
 * BackPressure: the returned flow is conflated. There is no mechanism to suspend an emission by
 * LiveData due to a slow collector, so collector always gets the most recent value emitted.
 */
fun <T> LiveData<T>.asFlow(): Flow<T> = flow {
    val channel = Channel<T>(Channel.CONFLATED)
    val observer = Observer<T> {
        channel.offer(it)
    }
    withContext(Dispatchers.Main.immediate) {
        observeForever(observer)
    }
    try {
        for (value in channel) {
            emit(value)
        }
    } finally {
        GlobalScope.launch(Dispatchers.Main.immediate) {
            removeObserver(observer)
        }
    }
}

@JvmOverloads
fun <T> Flow<T>.asLiveData(
    context: CoroutineContext = EmptyCoroutineContext,
    timeoutInMs: Long = 10
): LiveData<T> = liveData(context, timeoutInMs) {
    collect {
        emit(it)
    }
}