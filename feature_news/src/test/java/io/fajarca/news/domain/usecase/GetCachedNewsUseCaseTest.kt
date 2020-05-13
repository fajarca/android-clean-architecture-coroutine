package io.fajarca.news.domain.usecase

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
class GetCachedNewsUseCaseTest {

    private lateinit var sut: GetCachedNewsUseCase

    @Mock
    lateinit var repository: NewsRepository

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = GetCachedNewsUseCase(repository)
    }

    @Test
    fun `when country is not null, should get news from specified country` () = coroutineTestRule.runBlockingTest {
        //Given
        val country = "id"
        val category = null

        //When
        sut(country, category)

        //Then
        verify(repository).findByCountry(country)
    }

    @Test
    fun `when category is not null, should get news from specified category` () = coroutineTestRule.runBlockingTest {
        //Given
        val country = null
        val category = "technology"

        //When
        sut(country, category)

        //Then
        verify(repository).findByCategory(category)
    }


    @Test
    fun `when country and category is null, should get all news` () = coroutineTestRule.runBlockingTest {
        //Given
        val country = null
        val category = null

        //When
        sut(country, category)

        //Then
        verify(repository).findAll()
    }
}