package io.fajarca.news.data.mapper

import io.fajarca.core.database.entity.NewsEntity
import io.fajarca.news.data.response.NewsDto
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.MockitoAnnotations

class NewsMapperTest {

    private val FAKE_NEWS_TITLE = "Teknologi AI Tidak Terlalu Cocok Digunakan untuk Mencari Alien - SINDOnews.com"
    private val FAKE_NEWS_IMAGE_URL = "https://pict.sindonews.net/dyn/620/content/2020/02/11/124/1523392.jpg"
    private val FAKE_COUNTRY = "id"
    private val FAKE_CATEGORY = "technology"
    private lateinit var sut : NewsMapper


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = NewsMapper()
    }

    @Test
    fun testTransformNews() {
        //Given
        val response = createFakeNewsResponse()

        //When
        val output = sut.map(FAKE_COUNTRY, FAKE_CATEGORY, response)

        //Then
        assertEquals(output[0].country, FAKE_COUNTRY)
        assertEquals(output[0].category, FAKE_CATEGORY)
        assertEquals(output[0].title, FAKE_NEWS_TITLE)
        assertEquals(output[0].imageUrl, FAKE_NEWS_IMAGE_URL)
    }

    @Test
    fun `test transform news when given null values should convert all null values to empty string`() {
        //Given
        val response = createNullFakeNewsResponse()

        //When
        val output = sut.map(null, null, response)

        //Then
        assertEquals(output[0].country, "")
        assertEquals(output[0].category, "")
        assertEquals(output[0].title, "")
        assertEquals(output[0].imageUrl, "")
    }

    @Test
    fun testTransformNewsToDomain() {
        //Given
        val fakeNews = createFakeNewsEntity()

        //When
        val output = sut.mapToDomain(fakeNews)

        //Then
        assertEquals(output.title, FAKE_NEWS_TITLE)
        assertEquals(output.imageUrl, FAKE_NEWS_IMAGE_URL)
    }

    private fun createFakeNewsEntity(): NewsEntity {
        return NewsEntity(
            FAKE_NEWS_TITLE,
            "https://autotekno.sindonews.com/read/1523392/124/teknologi-ai-tidak-terlalu-cocok-digunakan-untuk-mencari-alien-1581400925",
            FAKE_NEWS_IMAGE_URL,
            "id",
            "technology",
            "2020-02-11T12:30:49Z",
            "",
            "cnn-indonesia"
        )
    }

    private fun createFakeNewsResponse(): NewsDto {
        val source = NewsDto.Article.Source("","")
        val articles = NewsDto.Article("", FAKE_NEWS_TITLE,"", FAKE_NEWS_IMAGE_URL, source)
        return NewsDto(arrayListOf(articles))
    }

    private fun createNullFakeNewsResponse(): NewsDto {
        val source = NewsDto.Article.Source(null,null)
        val articles = NewsDto.Article(null,null,null,null, source)
        return NewsDto(arrayListOf(articles))
    }
}