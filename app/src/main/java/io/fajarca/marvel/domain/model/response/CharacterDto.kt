package io.fajarca.marvel.domain.model.response


import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("data")
    val `data`: Data = Data()
) {
    data class Data(
        @SerializedName("results")
        val results: List<Result> = listOf()
    ) {
        data class Result(
            @SerializedName("id")
            val id: Long = 0,
            @SerializedName("name")
            val name: String = "",
            @SerializedName("thumbnail")
            val thumbnail: Thumbnail = Thumbnail()
        ) {
            data class Thumbnail(
                @SerializedName("extension")
                val extension: String = "",
                @SerializedName("path")
                val path: String = ""
            )
        }
    }
}