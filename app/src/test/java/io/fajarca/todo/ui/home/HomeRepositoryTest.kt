package io.fajarca.todo.ui.home

import io.fajarca.todo.data.mapper.CharactersMapper
import io.fajarca.todo.data.repository.HomeRepositoryImpl
import io.fajarca.todo.data.service.ApiService
import io.fajarca.todo.data.source.local.CharacterDao
import io.fajarca.todo.domain.model.common.Result
import io.fajarca.todo.domain.model.local.Character
import io.fajarca.todo.domain.model.response.CharacterDto
import io.fajarca.todo.util.TestCoroutineRule
import io.fajarca.todo.util.provideFakeCoroutinesDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class HomeRepositoryTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    @Mock
    lateinit var dao: CharacterDao
    @Mock
    lateinit var apiService: ApiService
    @Mock
    lateinit var mapper: CharactersMapper

    private lateinit var repository: HomeRepositoryImpl



    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        repository = HomeRepositoryImpl(
            provideFakeCoroutinesDispatcherProvider(testCoroutineDispatcher, testCoroutineDispatcher, testCoroutineDispatcher),
            apiService,
            mapper,
            dao
        )
    }


    @Test
    fun `when inserting new characters, should call dao insert method`() = runBlockingTest {
        repository.insertAllCharacter(emptyList())
        verify(dao).insertAll(emptyList())
    }


    @Test
    fun `when get all  characters, should call dao find all method`() = runBlockingTest {
        repository.getAllCharactersFromDb()
        verify(dao).findAll()
    }

    @Test
    fun `when get all  characters from remote, should call dao find all method`() = testCoroutineRule.runBlockingTest {
       val out = listOf<Character>()
        val dto = CharacterDto()

        `when`(mapper.mapToEntity(dto)).thenReturn(out)

        repository.getAllCharacter()

        verify(dao).insertAll(out)
    }


}