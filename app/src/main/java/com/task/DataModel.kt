package com.task

data class DataModel(
  var genres: ArrayList<Genres>? = null,
)

data class Genres(
  var id: Int = 0,
  var name: String = "",
)
