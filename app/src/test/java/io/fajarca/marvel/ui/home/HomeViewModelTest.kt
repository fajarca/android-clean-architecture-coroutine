package io.fajarca.marvel.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import io.fajarca.home.domain.usecase.GetCharactersUseCase
import io.fajarca.home.ui.home.HomeViewModel
import io.fajarca.marvel.util.TestCoroutineRule
import io.fajarca.marvel.util.TestUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    // Run tasks synchronously
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: HomeViewModel
    

    @Mock
    private lateinit var observer : Observer<HomeViewModel.Result<List<Character>>>


    @Mock
    private lateinit var useCase: GetCharactersUseCase

    @Mock
    lateinit var lifeCycleOwner: LifecycleOwner
    lateinit var lifeCycle: LifecycleRegistry

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = HomeViewModel(useCase)

        lifeCycle = LifecycleRegistry(lifeCycleOwner)
        `when` (lifeCycleOwner.lifecycle).thenReturn(lifeCycle)
        lifeCycle.handleLifecycleEvent(Lifecycle.Event.ON_START)

        viewModel.characters.observe(lifeCycleOwner, observer)


    }

    @Test
    fun `when get all all character is success, observer should receive success result`() = testCoroutineRule.runBlockingTest  {
        //Given
        val flowOfCharacters = flowOf(TestUtil.generateDummyCharacters(4))
        `when`(useCase.execute()).thenReturn(flowOfCharacters)

        //When
        viewModel.getAllCharacters()

        //Then
        flowOfCharacters.collect {
            verify(observer).onChanged(HomeViewModel.Result.Loading)
            verify(observer).onChanged(HomeViewModel.Result.Success(it))
        }
    }



}