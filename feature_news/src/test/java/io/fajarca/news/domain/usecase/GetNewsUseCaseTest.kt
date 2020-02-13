package io.fajarca.news.domain.usecase

import io.fajarca.news.domain.repository.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetNewsUseCaseTest {

    private lateinit var sut: GetNewsUseCase

    @Mock
    lateinit var repository: NewsRepository
    private val testCoroutineDispatcher = TestCoroutineDispatcher()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = GetNewsUseCase(repository)
    }

    @Test
    fun `when use case is executed, should get news from repository` () = testCoroutineDispatcher.runBlockingTest {
        //Given
        val country = "id"
        val category = "technology"
        val page = 1
        val pageSize = 25
        val onSuccessAction = { }

        //When
        sut.execute(country, category, page, pageSize, onSuccessAction)

        //Then
        verify(repository).findAllNews(country, category, page, pageSize, onSuccessAction)
    }
}