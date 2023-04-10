package com.repositories

import com.dto.MovieDetailsDTO
import com.dto.MovieListDTO
import com.services.ApiService
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class MovieDataRepository @Inject constructor(
  private var apiService: ApiService,
) {
  fun getPopularList(page: Int): Flow<Response<MovieListDTO>> = flow {
    val request = apiService.getPopularList(page = page)
    if (request.isSuccessful)
      emit(request)
  }.flowOn(IO)// chạy trong IO được ưu tiên

  fun getMovieDetail(movie_id: Int): Flow<Response<MovieDetailsDTO>> = flow {
    val request = apiService.getMovieDetail(movie_id)
    if (request.isSuccessful)
      emit(request)
  }.flowOn(IO)// chạy trong IO được ưu tiên
}