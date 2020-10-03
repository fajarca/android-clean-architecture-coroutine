package io.fajarca.core.network

import io.fajarca.core.vo.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class RemoteDataSourceTest {

    private val dispatcher = TestCoroutineDispatcher()
    private lateinit var dataSource: RemoteDataSource


    @Before
    fun setUp() {
        dataSource = RemoteDataSource()
    }


    @Test
    fun `when lambda returns successfully then it should emit the result as success`() {
        runBlockingTest {
            val lambdaResult = true
            val result = dataSource.safeApiCall(dispatcher) { lambdaResult }
            assertEquals(Result.Success(lambdaResult), result)
        }
    }

    @Test
    fun `when lambda throws SocketTimeOutException then it should emit the result as timeout error`() {
        runBlockingTest {
            val result = dataSource.safeApiCall(dispatcher) { throw SocketTimeoutException() }
            assertEquals(Result.Error(HttpResult.TIMEOUT), result)
        }
    }

    @Test
    fun `when lambda throws UnknownHostException then it should emit the result as no connection error`() {
        runBlockingTest {
            val result = dataSource.safeApiCall(dispatcher) { throw UnknownHostException() }
            assertEquals(Result.Error(HttpResult.NO_CONNECTION), result)
        }
    }

    @Test
    fun `when lambda throws IOException then it should emit the result as bad response error`() {
        runBlockingTest {
            val result = dataSource.safeApiCall(dispatcher) { throw IOException() }
            assertEquals(Result.Error(HttpResult.BAD_RESPONSE), result)
        }
    }

    @Test
    fun `when lambda throws other exception then it should emit the result as not defined error`() {
        runBlockingTest {
            val result = dataSource.safeApiCall(dispatcher) { throw Exception() }
            assertEquals(Result.Error(HttpResult.NOT_DEFINED), result)
        }
    }
}