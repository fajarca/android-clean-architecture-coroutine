package io.fajarca.feature.data

import io.fajarca.core.common.Mapper
import io.fajarca.feature.domain.CharacterDetail
import io.fajarca.core.common.Result

class CharacterDetailMapper : Mapper<Result<CharacterDetailDto>, Result<CharacterDetail>>(){

    override fun map(input: Result<CharacterDetailDto>): Result<CharacterDetail> {
        return when(input) {
            is Result.Loading -> {
                Result.Loading
            }
            is Result.Success -> {
                val detail = input.data.data.results[0]
                Result.Success(CharacterDetail(detail.id, detail.name, detail.description, "${detail.thumbnail.path}/portrait_uncanny.${detail.thumbnail.extension}"))
            }
            is Result.Error -> {
                Result.Error(input.cause, input.code, input.errorMessage)
            }
        }
    }


}