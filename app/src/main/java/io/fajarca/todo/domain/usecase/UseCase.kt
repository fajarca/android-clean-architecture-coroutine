package io.fajarca.todo.domain.usecase

import io.fajarca.todo.data.RemoteHandler
import io.fajarca.todo.domain.model.common.Result
import io.fajarca.todo.ui.CoroutinesDispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class UseCase @Inject constructor(private val remoteHandler: RemoteHandler) {
  /*  fun <T> execute(scope : CoroutineScope, dispatcher : CoroutinesDispatcherProvider) : Result<T> {
        scope.launch(dispatcher.io) {
            try {

            } catch (e : Exception) {
                return Result.error(remoteHandler.map(e))
            }
        }
    }*/
}