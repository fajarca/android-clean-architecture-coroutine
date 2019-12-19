package io.fajarca.todo.data.remote.response


import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("status")
    val status: String = ""
) {
    data class Data(
        @SerializedName("count")
        val count: Int = 0,
        @SerializedName("limit")
        val limit: Int = 0,
        @SerializedName("offset")
        val offset: Int = 0,
        @SerializedName("results")
        val results: List<Result> = listOf(),
        @SerializedName("total")
        val total: Int = 0
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