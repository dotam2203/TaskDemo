package com.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Author: tamdt35@fpt.com.vn
 * Date: 12/04/2023
 */
@Parcelize
data class MovieDetails(
  val budget: Int?,
  val genres: List<String>?,
  val overview: String?,
  val posterPath: String?,
  val releaseDate: String?,
  val revenue: Int?,
  val runtime: Int?,
  val tagline: String?,
  val title: String?,
  val voteAverage: Double?,
) : Parcelable