package io.fajarca.home.domain.entities

data class News(val id: Int, val title: String, val url : String, val imageUrl: String, val publishedAt : String, val sourceId : String, val sourceName : String)