package io.fajarca.todo.ui.home

import io.fajarca.todo.data.local.dao.TodoDao
import io.fajarca.todo.data.local.entity.Todo
import io.fajarca.todo.data.repository.HomeRepository
import io.fajarca.todo.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class HomeRepositoryTest {

    @Mock
    lateinit var dao: TodoDao

    private lateinit var repository: HomeRepository

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = HomeRepository(dao)
    }


    @Test
    fun `when insert is invoked should insert todo` () = testCoroutineRule.runBlockingTest{
        repository.insert(Todo(TITLE, DESCRIPTION))
        verify(dao).insert(Todo(TITLE,DESCRIPTION))
    }

    @Test
    fun `should return all todo app when invoked` () = testCoroutineRule.runBlockingTest{
        repository.findAll()
        verify(dao).findAll()
    }

    companion object {
        private const val TITLE = "Title"
        private const val DESCRIPTION = "Description"
    }


}