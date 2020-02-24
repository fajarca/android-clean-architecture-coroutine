package io.fajarca.news.presentation

import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class CharactersViewModelTest {

    /*// Run tasks synchronously
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: HomeViewModel
    

    @Mock
    private lateinit var observer : Observer<HomeViewModel.CharacterState<List<News>>>


    @Mock
    private lateinit var useCase: GetTopHeadlinesUseCase

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
        val marvelCharacters = mutableListOf<News>()
        marvelCharacters.add(
            News(
                1,
                "Marvel",
                "image-url"
            )
        )

        `when`(useCase.execute()).thenReturn(marvelCharacters)

        //When
        viewModel.getAllCharacters()

        //Then
        verify(observer).onChanged(HomeViewModel.CharacterState.Loading)
        verify(observer).onChanged(HomeViewModel.CharacterState.Success(marvelCharacters))

    }

    @Test
    fun `when get all all character is empty, observer should receive empty result`() = testCoroutineRule.runBlockingTest  {
        //Given
        val marvelCharacters = emptyList<News>()

        `when`(useCase.execute()).thenReturn(marvelCharacters)

        //When
        viewModel.getAllCharacters()

        //Then
        verify(observer).onChanged(HomeViewModel.CharacterState.Loading)
        verify(observer).onChanged(HomeViewModel.CharacterState.Empty)

    }*/

}