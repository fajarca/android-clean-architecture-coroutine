package io.fajarca.home.util

import io.fajarca.home.domain.MarvelCharacter

object TestUtil {

    fun generateDummyCharacter(id : Long, title : String = "Title", imageUrl : String = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available/portrait_uncanny.jpg" ) : MarvelCharacter {
        return MarvelCharacter(
            id = id,
            title = title,
            imageUrl = imageUrl
        )
    }
    fun generateDummyCharacters(numOfIteration : Int): List<MarvelCharacter> {
        val characters = mutableListOf<MarvelCharacter>()
        for (i in 1 .. numOfIteration) {
            characters.add(generateDummyCharacter(i.toLong()))
        }
        return characters
    }

}