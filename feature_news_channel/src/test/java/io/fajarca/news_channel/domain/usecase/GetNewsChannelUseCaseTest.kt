package io.fajarca.news_channel.domain.usecase

import io.fajarca.news_channel.domain.repository.NewsChannelRepository
import io.fajarca.testutil.extension.runBlockingTest
import io.fajarca.testutil.rule.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetNewsChannelUseCaseTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var repository: NewsChannelRepository
    private lateinit var sut : GetNewsChannelUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = GetNewsChannelUseCase(repository)
    }

    @Test
    fun `when use case is invoked, should find all news channel from repository`() = coroutineTestRule.runBlockingTest {
        //Given

        //When
        sut.invoke()

        //Then
        verify(repository).findAllNewsChannel()
    }
}