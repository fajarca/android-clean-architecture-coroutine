package io.fajarca.todo.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.fajarca.todo.domain.model.local.Todo
import io.fajarca.todo.domain.repository.HomeRepository
import io.fajarca.todo.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class HomeViewModelTest {
/*
    // Run tasks synchronously
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: HomeViewModel

    @Mock
    lateinit var repository: HomeRepository

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var observer : Observer<Result<List<Todo>>>

    companion object {
        private const val EMPTY_STRING_TITLE = ""
        private const val EMPTY_STRING_DESCRIPTION = ""
        private const val TITLE = "Title"
        private const val DESCRIPTION = "Description"
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = HomeViewModel(
            repository, provideFakeCoroutinesDispatcherProvider(
                testCoroutineDispatcher,
                testCoroutineDispatcher,
                testCoroutineDispatcher
            )
        )

        viewModel.todo.observeForever(observer)

    }


    @Test
    fun shouldProduceTrue_WhenTitleIsNotEmptyOrNull() {
        val actual = viewModel.isTitleValid("Title")
        assertEquals(true, actual)


    }

    @Test
    fun shouldProduceFalse_WhenTitleIsEmptyOrNull() {
        val actual = viewModel.isTitleValid("")
        assertEquals(false, actual)
    }

    @Test
    fun shouldProduceTrue_WhenDescriptionIsNotEmptyOrNull() {
        val actual = viewModel.isDescriptionValid("Description")
        assertEquals(true, actual)


    }

    @Test
    fun shouldProduceFalse_WhenDescriptionIsEmptyOrNull() {
        val output = viewModel.isDescriptionValid("")
        assertEquals(false, output)
    }


    @Test
    fun `When title or description is empty string, insert should not invoked `() =
        testCoroutineRule.runBlockingTest {
            viewModel.validateInput(EMPTY_STRING_TITLE, EMPTY_STRING_DESCRIPTION)
            verify(repository, never()).insert(
                Todo(
                    EMPTY_STRING_TITLE,
                    EMPTY_STRING_DESCRIPTION
                )
            )
        }

    @Test
    fun `When title or description is valid, insert should be invoked `() =
        testCoroutineRule.runBlockingTest {
            viewModel.validateInput(TITLE, DESCRIPTION)
            verify(repository).insert(Todo(TITLE, DESCRIPTION))
        }


    @Test
    fun `When request all todo, should return all record`() = testCoroutineRule.runBlockingTest {

        val data = flow {
            emit(emptyList<Todo>())
        }

        `when`(repository.findAll()).thenReturn(data)


        val firstResult = viewModel.todo.value
        assertEquals(0, firstResult?.data?.size ?: 0)

        viewModel.getTodo()

        val secondResult = viewModel.todo.value
        assertEquals(2, secondResult?.data?.size)
    }*/


}