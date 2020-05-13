package io.fajarca.news.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.fajarca.news.domain.entities.SearchQuery
import io.fajarca.news.domain.usecase.GetCachedNewsUseCase
import io.fajarca.news.domain.usecase.InsertNewsUseCase
import io.fajarca.news.presentation.mapper.NewsPresentationMapper
import io.fajarca.testutil.LifeCycleTestOwner
import io.fajarca.testutil.rule.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var lifecycleOwner: LifeCycleTestOwner
    private lateinit var sut : HomeViewModel

    @Mock private lateinit var useCaseCached : GetCachedNewsUseCase
    @Mock private lateinit var insertNewsUseCase: InsertNewsUseCase
    @Mock private lateinit var mapper : NewsPresentationMapper
    @Mock private lateinit var observer : Observer<Result<SearchQuery>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifecycleOwner = LifeCycleTestOwner()
        lifecycleOwner.onCreate()

        sut = HomeViewModel(useCaseCached, insertNewsUseCase, mapper)
    }
}