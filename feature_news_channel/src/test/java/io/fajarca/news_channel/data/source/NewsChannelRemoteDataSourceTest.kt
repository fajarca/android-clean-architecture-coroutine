package io.fajarca.news_channel.data.source

import io.fajarca.news_channel.data.ChannelService
import io.fajarca.testutil.extension.runBlockingTest
import io.fajarca.testutil.rule.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@UseExperimental(ExperimentalCoroutinesApi::class)
class NewsChannelRemoteDataSourceTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var sut : NewsChannelRemoteDataSource

    @Mock
    private lateinit var channelService: ChannelService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = NewsChannelRemoteDataSource(channelService)
    }

    @Test
    fun `when get channel, should fetch from network`() = coroutineTestRule.runBlockingTest {
        //Given

        //When
        sut.getNewsChannel(coroutineTestRule.testDispatcher)

        //Then
        verify(channelService).getNewsChannels()
    }

}