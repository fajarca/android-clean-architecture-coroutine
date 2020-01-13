package io.fajarca.feature.data

import io.fajarca.core.common.Mapper
import io.fajarca.feature.domain.CharacterDetail
import io.fajarca.core.common.Result

class CharacterMapper : Mapper<Result<CharacterDetailDto>, CharacterDetail>(){

    override fun map(input: Result<CharacterDetailDto>): CharacterDetail {
        val detail = CharacterDetail()
//        input.data.results.map {
//            detail.id = it.id
//            detail.description = it.description
//            detail.imageUrl = "${it.thumbnail.path}/portrait_uncanny.${it.thumbnail.extension}"
//            detail.name = it.name
//        }
        return detail
    }


}