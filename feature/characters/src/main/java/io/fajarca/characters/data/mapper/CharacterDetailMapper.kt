package io.fajarca.characters.data.mapper

import io.fajarca.characters.data.response.CharacterDetailDto
import io.fajarca.core.mapper.Mapper
import io.fajarca.core.vo.Result

class CharacterDetailMapper : Mapper<Result<CharacterDetailDto>, Result<io.fajarca.characters.domain.CharacterDetail>>(){

    override fun map(input: Result<CharacterDetailDto>): Result<io.fajarca.characters.domain.CharacterDetail> {
        return when(input) {
            is Result.Loading -> {
                Result.Loading
            }
            is Result.Success -> {
                val detail = input.data.data.results[0]
                Result.Success(
                    io.fajarca.characters.domain.CharacterDetail(
                        detail.id,
                        detail.name,
                        detail.description,
                        "${detail.thumbnail.path}/portrait_uncanny.${detail.thumbnail.extension}"
                    )
                )
            }
            is Result.Error -> {
                Result.Error(input.cause, input.code, input.errorMessage)
            }
        }
    }


}