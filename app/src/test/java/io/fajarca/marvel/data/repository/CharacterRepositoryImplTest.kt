package io.fajarca.marvel.data.repository

import io.fajarca.marvel.data.mapper.CharactersMapper
import io.fajarca.marvel.data.source.local.CharacterDao
import io.fajarca.marvel.data.source.remote.CharacterRemoteDataSource
import io.fajarca.marvel.domain.model.common.HttpResult
import io.fajarca.marvel.domain.model.common.Result
import io.fajarca.marvel.domain.model.response.CharacterDto
import io.fajarca.marvel.util.TestUtil
import io.fajarca.marvel.util.provideFakeCoroutinesDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class CharacterRepositoryImplTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var dao: CharacterDao
    @Mock
    lateinit var mapper: CharactersMapper
    @Mock
    lateinit var remoteDataSource: CharacterRemoteDataSource

    private lateinit var repository: CharacterRepositoryImpl


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        repository = CharacterRepositoryImpl(
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
    fun `when get all characters from remote is success, should insert the characters to db`() =
        runBlockingTest {
            //Given
            val characters = TestUtil.generateDummyCharacters(numOfIteration = 0)
            val response = Result.Success(CharacterDto())

            `when`(remoteDataSource.getCharacters(testCoroutineDispatcher)).thenReturn(response)

            //When
            repository.getAllCharacter()

            //Then
            verify(dao).insertAll(characters)
        }

    @Test
    fun `when get all characters from remote is error, insert character to db won't executed`() =
        runBlockingTest {
            //Given
            val characters = TestUtil.generateDummyCharacters(numOfIteration = 5)
            val response = Result.Error(HttpResult.NO_CONNECTION)

            `when`(remoteDataSource.getCharacters(testCoroutineDispatcher)).thenReturn(response)

            //When
            repository.getAllCharacter()

            //Then
            verify(dao, never()).insertAll(characters)
        }

}