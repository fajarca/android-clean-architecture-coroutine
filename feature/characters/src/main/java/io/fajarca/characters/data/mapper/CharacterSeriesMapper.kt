package io.fajarca.characters.data.mapper

import io.fajarca.characters.data.response.CharacterSeriesDto
import io.fajarca.characters.domain.entities.MarvelCharacterSeries
import io.fajarca.core.mapper.Mapper
import io.fajarca.core.vo.Result

class CharacterSeriesMapper : Mapper<Result<CharacterSeriesDto>, Result<List<MarvelCharacterSeries>>>(){

    override fun map(input: Result<CharacterSeriesDto>): Result<List<MarvelCharacterSeries>> {
        return when(input) {
            is Result.Loading -> {
                Result.Loading
            }
            is Result.Success -> {
                val mappedSeries = mutableListOf<MarvelCharacterSeries>()
                val series = input.data.data.results
                series.map {
                    mappedSeries.add(MarvelCharacterSeries(it.id,
                        it.title,
                        "${it.thumbnail.path}/portrait_uncanny.${it.thumbnail.extension}",
                        mapMarvelWebUrl(it.urls),
                        it.startYear,
                        it.endYear,
                        it.rating,
                        mapSeriesCharacter(it.characters.items)
                    ))
                }

                Result.Success(mappedSeries)
            }
            is Result.Error -> {
                Result.Error(input.cause, input.code, input.errorMessage)
            }
        }
    }

    private fun mapSeriesCharacter(series: List<CharacterSeriesDto.Data.Result.Characters.Item>): List<String> {
        val characters = mutableListOf<String>()
        series.map { characters.add(it.name) }
        return characters
    }

    private fun mapMarvelWebUrl(urls: List<CharacterSeriesDto.Data.Result.Url>): String {
        return if (urls.isEmpty()) "" else urls[0].url
    }


}