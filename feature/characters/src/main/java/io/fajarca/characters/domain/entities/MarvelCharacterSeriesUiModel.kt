package io.fajarca.characters.domain.entities

data class MarvelCharacterSeriesUiModel(val id : Int, val name : String, val imageUrl : String, val webUrl : String, val startYear : Int, val endYear : Int, val rating : Float, val seriesCharacters : String)