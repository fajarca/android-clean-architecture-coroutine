package io.fajarca.news.presentation.mapper

import io.fajarca.news.domain.entities.News
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class NewsPresentationMapperTest {

    private lateinit var sut: NewsPresentationMapper

    @Before
    fun setUp() {
        sut  = NewsPresentationMapper()
    }

    @Test
    fun `when given news publishedAt then convert it to local time`() {
        //Given
        val input =  News(
            "Teknologi AI Tidak Terlalu Cocok Digunakan untuk Mencari Alien - SINDOnews.com",
            "https://autotekno.sindonews.com/read/1523392/124/teknologi-ai-tidak-terlalu-cocok-digunakan-untuk-mencari-alien-1581400925",
            "https://pict.sindonews.net/dyn/620/content/2020/02/11/124/1523392.jpg",
            "2020-02-11T12:30:49Z",
            "technology",
            "cnn",
            "CNN"
        )

        val expected =  News(
            "Teknologi AI Tidak Terlalu Cocok Digunakan untuk Mencari Alien - SINDOnews.com",
            "https://autotekno.sindonews.com/read/1523392/124/teknologi-ai-tidak-terlalu-cocok-digunakan-untuk-mencari-alien-1581400925",
            "https://pict.sindonews.net/dyn/620/content/2020/02/11/124/1523392.jpg",
            "7:30 PM",
            "technology",
            "cnn",
            "CNN"
        )

        val locale = Locale.getDefault()

        //When
        val actual = sut.map(input, locale)

        //Then
        assertEquals(expected, actual)
    }
}
