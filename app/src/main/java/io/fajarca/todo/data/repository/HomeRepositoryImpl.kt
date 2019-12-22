package io.fajarca.todo.data.repository

import io.fajarca.todo.data.mapper.CharactersMapper
import io.fajarca.todo.data.service.ApiService
import io.fajarca.todo.domain.model.Character
import io.fajarca.todo.domain.model.response.CharacterDto
import io.fajarca.todo.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val apiService: ApiService, private val mapper : CharactersMapper) :
    HomeRepository {

    override suspend fun getAllCharacters(): List<Character> {
        return mapper.map(apiService.getCharacters())
    }

}