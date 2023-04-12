package com.dto

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 12/04/2023
 */
object ConvertData {
  fun MovieDetailsDTO.toMovieDetails(): MovieDetails {
    val genreNames = genres?.mapNotNull { it?.name }
    return MovieDetails(budget, genreNames, overview, posterPath, releaseDate, revenue, runtime, tagline, title, voteAverage)
  }
}