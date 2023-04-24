package com.model

import android.os.Parcelable
import com.dto.MovieDetailsDTO
import kotlinx.parcelize.Parcelize

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 12/04/2023
 */
@Parcelize
data class MovieDetailModel(
  val budget: Int? = 0,
  val genres: List<String>? = emptyList(),
  val overview: String? = null,
  val posterPath: String? = null,
  val releaseDate: String? = null,
  val revenue: Int? = 0,
  val runtime: Int? = 0,
  val tagline: String? = null,
  val title: String? = null,
  val voteAverage: Double? = 0.0,
) : Parcelable

fun MovieDetailsDTO.toMovieDetailModel(): MovieDetailModel {
  val genreNames = genres?.mapNotNull { it?.name }
  return MovieDetailModel(budget, genreNames, overview, posterPath, releaseDate, revenue, runtime, tagline, title, voteAverage)
}