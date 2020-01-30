package io.fajarca.home.data

import io.fajarca.home.data.mapper.CharactersMapper
import io.fajarca.home.data.response.CharacterDto
import org.junit.Test

import org.junit.Before

class CharactersMapperTest {

    private lateinit var mapper: CharactersMapper
    @Before
    fun setup() {
        mapper = CharactersMapper()
    }


    @Test
    fun map() {
        //Given
        val thumbnail = CharacterDto.Data.Result.Thumbnail(".jpg", "http://img.marvel")

        val results = mutableListOf<CharacterDto.Data.Result>()
        results.add(CharacterDto.Data.Result(1, "Marvel", thumbnail))

        val input = CharacterDto(
            CharacterDto.Data(results)
        )

        //When
        mapper.map(input)

        //Then

    }

    @Test
    fun mapToDomain() {
    }
}