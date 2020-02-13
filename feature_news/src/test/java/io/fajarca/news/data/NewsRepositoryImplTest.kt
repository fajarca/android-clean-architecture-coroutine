package io.fajarca.news.data

import io.fajarca.core.database.dao.NewsDao
import io.fajarca.core.database.entity.NewsEntity
import io.fajarca.core.network.HttpResult
import io.fajarca.core.vo.Result
import io.fajarca.news.data.mapper.NewsMapper
import io.fajarca.news.data.response.NewsDto
import io.fajarca.news.data.source.NewsRemoteDataSource
import io.fajarca.news.util.provideFakeCoroutinesDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class NewsRepositoryImplTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private lateinit var sut: NewsRepositoryImpl

    @Mock
    lateinit var mapper: NewsMapper
    @Mock
    lateinit var dao: NewsDao
    @Mock
    lateinit var remoteDataSource: NewsRemoteDataSource

    private val FAKE_NEWS_TITLE =
        "Teknologi AI Tidak Terlalu Cocok Digunakan untuk Mencari Alien - SINDOnews.com"
    private val FAKE_NEWS_IMAGE_URL =
        "https://pict.sindonews.net/dyn/620/content/2020/02/11/124/1523392.jpg"

    private val country = "id"
    private val category = "technology"
    private val page = 1
    private val pageSize = 25

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = NewsRepositoryImpl(
            provideFakeCoroutinesDispatcherProvider(testCoroutineDispatcher),
            mapper,
            dao,
            remoteDataSource
        )
    }

    @Test
    fun `when inserting news, should call dao insert method`() = testCoroutineDispatcher.runBlockingTest {
        //Given
        val news = createFakeNewsEntities()

        //When
        sut.insertNews(news)

        //Then
        verify(dao).insertAll(news)
    }

    @Test
    fun `when fetch news from api, get news from network should be invoked`() = testCoroutineDispatcher.runBlockingTest {

        //When
        sut.getNewsFromApi(country, category, page, pageSize, {})

        //Then
        verify(remoteDataSource).getNews(testCoroutineDispatcher, country, category, page, pageSize)
    }

    @Test
    fun `when get all news success, save news to local db`() = runBlockingTest {
        //Given
        val news = createFakeNewsEntities()
        val response = Result.Success(createFakeNewsResponse())

        `when`(remoteDataSource.getNews(testCoroutineDispatcher, country, category, page, pageSize)).thenReturn(response)
        `when`(mapper.map(country, category, createFakeNewsResponse())).thenReturn(news)

        //When
        sut.findAllNews(country, category, page, pageSize, {})

        //Then
        verify(dao).insertAll(news)
    }

    @Test
    fun `when get all news failed, insert no news`() = runBlockingTest {
        //Given
        val news = createFakeNewsEntities()
        val response = Result.Error(HttpResult.NO_CONNECTION)

        `when`(remoteDataSource.getNews(testCoroutineDispatcher, country, category, page, pageSize)).thenReturn(response)

        //When
        sut.findAllNews(country, category, page, pageSize, {})

        //Then
        verify(dao, never()).insertAll(news)
    }

    private fun createFakeNewsEntities(): List<NewsEntity> {
        return arrayListOf(
            NewsEntity(
                FAKE_NEWS_TITLE,
                "https://autotekno.sindonews.com/read/1523392/124/teknologi-ai-tidak-terlalu-cocok-digunakan-untuk-mencari-alien-1581400925",
                FAKE_NEWS_IMAGE_URL,
                "id",
                "technology",
                "2020-02-11T12:30:49Z",
                "",
                "cnn-indonesia"
            )
        )

    }

    private fun createFakeNewsResponse(): NewsDto {
        val source = NewsDto.Article.Source("","")
        val articles = NewsDto.Article("", FAKE_NEWS_TITLE,"", FAKE_NEWS_IMAGE_URL, source)
        return NewsDto(arrayListOf(articles))
    }

}