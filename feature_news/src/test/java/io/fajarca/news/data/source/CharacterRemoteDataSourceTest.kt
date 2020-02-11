package io.fajarca.news.data.source

import io.fajarca.news.data.NewsService
import io.fajarca.core.network.HttpResult
import io.fajarca.core.vo.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class CharacterRemoteDataSourceTest {

    private lateinit var sut : NewsRemoteDataSource
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var characterService: NewsService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = NewsRemoteDataSource(characterService)
    }

    @Test
    fun `when get character, should fetch from network`() = runBlockingTest {
        //Given
        val params = "-modified"

        //When
        sut.getCharacters(testCoroutineDispatcher)

        //Then
        verify(characterService).getCharacters(params)
    }


    @Test
    fun `when get character detail, should fetch from network`() = runBlockingTest {
        //Given
        val characterId = 4

        //When
        sut.getCharacterDetail(characterId, testCoroutineDispatcher)

        //Then
        verify(characterService).getCharacterDetail(characterId)
    }


    @Test
    fun `when get character series, should fetch from network`() = runBlockingTest {
        //Given
        val characterId = 4

        //When
        sut.getCharacterSeries(characterId, testCoroutineDispatcher)

        //Then
        verify(characterService).getCharacterSeries(characterId)
    }

    @Test
    fun `when get character throw exception, should return error result`() = runBlockingTest {
        //Given
        val expected = Result.Error(HttpResult.NOT_DEFINED, null, null)
        val params = "-modified"

        `when`(characterService.getCharacters(params)).thenThrow(IllegalArgumentException::class.java)

        //When
        val actual = sut.getCharacters(testCoroutineDispatcher)

        assertEquals(expected, actual)
    }

    @Test
    fun `when get character detail throw exception, should return error result`() = runBlockingTest {
        //Given
        val expected = Result.Error(HttpResult.NOT_DEFINED, null, null)
        val characterId = 4

        `when`(characterService.getCharacterDetail(characterId)).thenThrow(IllegalArgumentException::class.java)

        //When
        val actual = sut.getCharacterDetail(characterId, testCoroutineDispatcher)

        assertEquals(expected, actual)
    }

    @Test
    fun `when get character series throw exception, should return error result`() = runBlockingTest {
        //Given
        val expected = Result.Error(HttpResult.NOT_DEFINED, null, null)
        val characterId = 4

        `when`(characterService.getCharacterSeries(characterId)).thenThrow(IllegalArgumentException::class.java)

        //When
        val actual = sut.getCharacterSeries(characterId, testCoroutineDispatcher)

        assertEquals(expected, actual)
    }
}