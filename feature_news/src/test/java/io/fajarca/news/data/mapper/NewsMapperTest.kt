package io.fajarca.news.data.mapper

import io.fajarca.core.database.entity.NewsEntity
import io.fajarca.news.data.response.NewsDto
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class NewsMapperTest {

    private val fakeNewsTitle = "Teknologi AI Tidak Terlalu Cocok Digunakan untuk Mencari Alien - SINDOnews.com"
    private val fakeNewsImageUrl = "https://pict.sindonews.net/dyn/620/content/2020/02/11/124/1523392.jpg"
    private val fakeCountry = "id"
    private val fakeCategory = "technology"
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
        val output = sut.map(fakeCountry, fakeCategory, response)

        //Then
        assertEquals(output[0].country, fakeCountry)
        assertEquals(output[0].category, fakeCategory)
        assertEquals(output[0].title, fakeNewsTitle)
        assertEquals(output[0].imageUrl, fakeNewsImageUrl)
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
        assertEquals(output.title, fakeNewsTitle)
        assertEquals(output.imageUrl, fakeNewsImageUrl)
    }

    private fun createFakeNewsEntity(): NewsEntity {
        return NewsEntity(
            fakeNewsTitle,
            "https://autotekno.sindonews.com/read/1523392/124/teknologi-ai-tidak-terlalu-cocok-digunakan-untuk-mencari-alien-1581400925",
            fakeNewsImageUrl,
            "id",
            "technology",
            "2020-02-11T12:30:49Z",
            "",
            "cnn-indonesia"
        )
    }

    private fun createFakeNewsResponse(): NewsDto {
        val source = NewsDto.Article.Source("","")
        val articles = NewsDto.Article("", fakeNewsTitle,"", fakeNewsImageUrl, source)
        return NewsDto(arrayListOf(articles))
    }

    private fun createNullFakeNewsResponse(): NewsDto {
        val source = NewsDto.Article.Source(null,null)
        val articles = NewsDto.Article(null,null,null,null, source)
        return NewsDto(arrayListOf(articles))
    }
}