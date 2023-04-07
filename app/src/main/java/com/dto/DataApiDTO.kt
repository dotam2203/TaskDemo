package com.dto

data class DataApiDTO(
  var genres: ArrayList<Genres>? = null,
)
data class Genres(
  var id: Int? = 0,
  var name: String? = ""
)
