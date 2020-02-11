package io.fajarca.news.domain.usecase

import io.fajarca.news.domain.repository.NewsRepository
import io.fajarca.core.network.HttpResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetCharactersSeriesUseCaseTest {

    private lateinit var sut : GetCharactersSeriesUseCase
    @Mock
    private lateinit var repository: NewsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = GetCharactersSeriesUseCase(repository)
    }

    @Test
    fun `when use case is executed, should get character series from repository`() = runBlockingTest {
        //Given
        val characterId = 4

        //When
        sut.execute(characterId, {}, { cause: HttpResult, code: Int?, errorMessage: String? ->  })

        //Then
        verify(repository).getCharacterSeries(characterId)
    }
}