package io.fajarca.news.domain.usecase

import io.fajarca.core.network.HttpResult
import io.fajarca.news.domain.repository.NewsRepository
import io.fajarca.testutil.extension.runBlockingTest
import io.fajarca.testutil.rule.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class InsertNewsUseCaseTest {

    private lateinit var sut: InsertNewsUseCase

    @Mock
    lateinit var repository: NewsRepository

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = InsertNewsUseCase(repository)
    }

    @Test
    fun `when use case is invoked, should get news from repository` () = coroutineTestRule.runBlockingTest {
        //Given
        val country = "id"
        val category = null
        val page = 1
        val pageSize = 25
        val onSuccessAction = { }
        val onErrorAction = { httpResult : HttpResult, code : Int, errorMessage : String -> }

        //When
        sut(country, category, page, pageSize, onSuccessAction, onErrorAction)

        //Then
        verify(repository).findAllNews(country, category, page, pageSize, onSuccessAction, onErrorAction)
    }
}