package io.fajarca.todo.data.repository

import io.fajarca.todo.data.mapper.CharactersMapper
import io.fajarca.todo.data.repository.HomeRepositoryImpl
import io.fajarca.todo.data.service.ApiService
import io.fajarca.todo.data.source.local.CharacterDao
import io.fajarca.todo.data.source.remote.CharacterRemoteDataSource
import io.fajarca.todo.domain.model.common.HttpResult
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
import java.lang.IllegalArgumentException

@ExperimentalCoroutinesApi
class HomeRepositoryImplTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var dao: CharacterDao
    @Mock
    lateinit var mapper: CharactersMapper
    @Mock
    lateinit var remoteDataSource: CharacterRemoteDataSource

    private lateinit var repository: HomeRepositoryImpl


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        repository = HomeRepositoryImpl(
            provideFakeCoroutinesDispatcherProvider(testCoroutineDispatcher),
            mapper,
            dao,
            remoteDataSource
        )
    }


    @Test
    fun `when inserting new characters, should call dao insert method`() = runBlockingTest {
        repository.insertAllCharacter(emptyList())
        verify(dao).insertAll(emptyList())
    }


    @Test
    fun `when get all characters from db, should invoke dao find all method`() = runBlockingTest {
        repository.getAllCharactersFromDb()
        verify(dao).findAll()
    }

    @Test
    fun `when get all  characters from remote is success, should insert to db`() =
        runBlockingTest {
            //Given
            val characters = listOf<Character>()
            val response = Result.Success(CharacterDto())

            `when`(remoteDataSource.getCharacters(testCoroutineDispatcher)).thenReturn(response)

            //When
            repository.getAllCharacter()

            //Then
            verify(dao).insertAll(characters)
        }

    @Test
    fun `when get all  characters from remote is error, dont insert to db`() =
        runBlockingTest {
            //Given
            val characters = listOf<Character>()
            val response = Result.Error(HttpResult.NO_CONNECTION)

            `when`(remoteDataSource.getCharacters(testCoroutineDispatcher)).thenReturn(response)

            //When
            repository.getAllCharacter()

            //Then
            verify(dao, never()).insertAll(characters)
        }

}