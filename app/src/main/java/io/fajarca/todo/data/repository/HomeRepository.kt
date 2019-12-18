package io.fajarca.todo.data.repository

import android.content.SharedPreferences
import io.fajarca.todo.data.local.dao.TodoDao
import io.fajarca.todo.data.local.entity.Todo
import io.fajarca.todo.data.remote.HomeRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepository @Inject constructor(private val dao: TodoDao,
                                         private val remoteDataSource: HomeRemoteDataSource) {


    suspend fun insert(todo: Todo) = dao.insert(todo)

    fun findAll(): Flow<List<Todo>>  =  dao.findAll()

    fun deleteById(id : Long) = dao.deleteById(id)

    suspend fun getAllNowPlaying() = remoteDataSource.nowPlaying()

}


