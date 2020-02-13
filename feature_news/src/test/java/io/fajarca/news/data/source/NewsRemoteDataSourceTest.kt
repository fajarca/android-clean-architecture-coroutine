package io.fajarca.news.data.source

import io.fajarca.news.data.NewsService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class NewsRemoteDataSourceTest {

    @Mock
    lateinit var newsService : NewsService
    private lateinit var sut : NewsRemoteDataSource
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val country = "id"
    private val category = "technology"
    private val page = 1
    private val pageSize = 25

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = NewsRemoteDataSource(newsService)
    }

    @Test
    fun `when get news, should fetch from network`() = testCoroutineDispatcher.runBlockingTest{
        //Given

        //When
        sut.getNews(testCoroutineDispatcher, country, category, page, pageSize)

        //Then
        verify(newsService).getNews( country, category, page, pageSize)
    }

}