package io.fajarca.marvel.util

object TestUtil {

    fun generateDummyCharacter(id : Long, title : String = "Title", imageUrl : String = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available/portrait_uncanny.jpg" ) : Character {
        return Character(
            id = id,
            title = title,
            imageUrl = imageUrl
        )
    }
    fun generateDummyCharacters(numOfIteration : Int): List<Character> {
        val characters = mutableListOf<Character>()
        for (i in 1 .. numOfIteration) {
            characters.add(generateDummyCharacter(i.toLong()))
        }
        return characters
    }

}