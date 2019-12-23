package io.fajarca.todo.base

import io.fajarca.todo.data.source.remote.safeApiCall
import io.fajarca.todo.domain.model.common.HttpResult
import io.fajarca.todo.domain.model.common.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class UtilKtTest {

    private val dispatcher = TestCoroutineDispatcher()

    @Test
    fun `when lambda returns successfully then it should emit the result as success`() {
        runBlockingTest {
            val lambdaResult = true
            val result = safeApiCall(dispatcher) { lambdaResult }
            assertEquals(Result.Success(lambdaResult), result)
        }
    }

    @Test
    fun `when lambda throws SocketTimeOutException then it should emit the result as error`() {
        runBlockingTest {
            val result =
                safeApiCall(dispatcher) { throw SocketTimeoutException() }
            assertEquals(Result.Error(HttpResult.TIMEOUT), result)
        }
    }

    @Test
    fun `when lambda throws UnknownHostException then it should emit the result as error`() {
        runBlockingTest {
            val result =
                safeApiCall(dispatcher) { throw UnknownHostException() }
            assertEquals(Result.Error(HttpResult.NO_CONNECTION), result)
        }
    }

    @Test
    fun `when lambda throws IOException then it should emit the result as error`() {
        runBlockingTest {
            val result =
                safeApiCall(dispatcher) { throw IOException() }
            assertEquals(Result.Error(HttpResult.BAD_RESPONSE), result)
        }
    }

    @Test
    fun `when lambda throws other exception then it should emit the result as error`() {
        runBlockingTest {
            val result =
                safeApiCall(dispatcher) { throw Exception() }
            assertEquals(Result.Error(HttpResult.NOT_DEFINED), result)
        }
    }
}