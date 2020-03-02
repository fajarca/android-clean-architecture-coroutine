package io.fajarca.news_channel.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.fajarca.news_channel.domain.entities.NewsChannel
import io.fajarca.news_channel.domain.usecase.GetNewsChannelUseCase
import io.fajarca.news_channel.presentation.mapper.NewsChannelPresentationMapper
import io.fajarca.testutil.LifeCycleTestOwner
import io.fajarca.testutil.extension.runBlockingTest
import io.fajarca.testutil.rule.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@UseExperimental(ExperimentalCoroutinesApi::class)
class NewsChannelViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var lifecycleOwner: LifeCycleTestOwner
    private lateinit var sut: NewsChannelViewModel
    @Mock
    private lateinit var mapper: NewsChannelPresentationMapper
    @Mock
    private lateinit var useCase: GetNewsChannelUseCase
    @Mock
    private lateinit var observer: Observer<NewsChannelViewModel.NewsChannelState>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        lifecycleOwner = LifeCycleTestOwner()
        lifecycleOwner.onCreate()

        sut = NewsChannelViewModel(useCase, mapper)
        sut.newsChannel.observe(lifecycleOwner, observer)
    }

    @After
    fun tearDown() {
        lifecycleOwner.onDestroy()
    }

    @Test
    fun `when given non empty data, should emit success state to observer`() = coroutineTestRule.runBlockingTest {
        //Given
        lifecycleOwner.onResume()

        val channels = arrayListOf(NewsChannel("cnn-indonesia", "id", "CNN Indonesia", "cnn-indonesia.com", "CI"))

        `when`(useCase.invoke()).thenReturn(channels)
        `when`(mapper.map(channels)).thenReturn(channels)

        //When
        sut.getNewsChannel()


        //Then
        verify(observer).onChanged(NewsChannelViewModel.NewsChannelState.Loading)
        verify(observer).onChanged(NewsChannelViewModel.NewsChannelState.Success(channels))
    }

    @Test
    fun `when given empty data, should emit empty state to observer`() = coroutineTestRule.runBlockingTest {
        //Given
        lifecycleOwner.onResume()

        val channels = emptyList<NewsChannel>()

        `when`(useCase.invoke()).thenReturn(channels)

        //When
        sut.getNewsChannel()


        //Then
        verify(observer).onChanged(NewsChannelViewModel.NewsChannelState.Loading)
        verify(observer).onChanged(NewsChannelViewModel.NewsChannelState.Empty)
    }
}