package io.fajarca.characters.domain.usecase

import io.fajarca.characters.domain.repository.CharacterRepository
import io.fajarca.core.network.HttpResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetCharactersUseCaseTest {


    private lateinit var sut : GetCharactersUseCase
    @Mock
    private lateinit var repository: CharacterRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = GetCharactersUseCase(repository)
    }

    @Test
    fun `when use case is executed, should get character detail from repository`() = runBlockingTest {
        //Given

        //When
        sut.execute()

        //Then
        verify(repository).getAllCharacter()
    }

}