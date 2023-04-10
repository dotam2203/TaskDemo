package com.services

import com.apis.Constants
import com.apis.Constants.API_KEY
import com.dto.MovieDetailsDTO
import com.dto.MovieListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Author: tamdt35@fpt.com.vn
 * Date:  15/12/2022
 */
interface ApiService {
  @GET("popular")
  suspend fun getPopularList(
    @Query("api_key")
    api_key: String = API_KEY,
    @Query("language")
    language: String = "en-US",
    @Query("page")
    page: Int,
  ): Response<MovieListDTO>

  @GET("{movie_id}")
  suspend fun getMovieDetail(
    @Path("movie_id")
    movie_id: Int,
    @Query("api_key")
    api_key: String = API_KEY,
    @Query("language")
    language: String = "en-US",
  ): Response<MovieDetailsDTO>
}