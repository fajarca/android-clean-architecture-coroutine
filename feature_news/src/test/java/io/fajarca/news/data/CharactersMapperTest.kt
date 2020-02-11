package io.fajarca.news.data

import io.fajarca.news.data.mapper.NewsMapper
import org.junit.Test

import org.junit.Before

class CharactersMapperTest {

    private lateinit var mapper: NewsMapper
    @Before
    fun setup() {
        mapper = NewsMapper()
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