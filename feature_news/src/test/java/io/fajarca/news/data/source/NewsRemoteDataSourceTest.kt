package io.fajarca.news.data.source

import io.fajarca.news.data.NewsService
import io.fajarca.testutil.extension.runBlockingTest
import io.fajarca.testutil.rule.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
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

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = NewsRemoteDataSource(newsService)
    }

    @Test
    fun `when get news, should fetch from network`() = coroutineTestRule.runBlockingTest{
        //Given

        //When
        sut.getNews(testCoroutineDispatcher, country, category, page, pageSize)

        //Then
        verify(newsService).getNews( country, category, page, pageSize)
    }

}