package io.fajarca.home.util

import io.fajarca.core.database.CharacterEntity

object TestUtil {

    fun generateDummyCharacter(id : Int, title : String = "Title", imageUrl : String = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available/portrait_uncanny.jpg" ) : CharacterEntity {
        return CharacterEntity(
            id = id,
            title = title,
            imageUrl = imageUrl
        )
    }
    fun generateDummyCharacters(numOfIteration : Int): List<CharacterEntity> {
        val characters = mutableListOf<CharacterEntity>()
        for (i in 1 .. numOfIteration) {
            characters.add(generateDummyCharacter(i))
        }
        return characters
    }

}