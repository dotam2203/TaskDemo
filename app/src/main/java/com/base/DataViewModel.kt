package com.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dto.MovieDetailsDTO
import com.dto.MovieListDTO
import com.repositories.MovieDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
  private val dataRepository: MovieDataRepository,
) : ViewModel() {
  //movie list
  private val _movies: MutableStateFlow<MovieListDTO?> = MutableStateFlow(null)
  val movies: StateFlow<MovieListDTO?> = _movies

  //movie detail
  private val _movie: MutableStateFlow<MovieDetailsDTO?> = MutableStateFlow(null)
  var movie: StateFlow<MovieDetailsDTO?> = _movie

  fun getMovieList(page: Int) {
    viewModelScope.launch {
      dataRepository.getPopularList(page).collect {
        if (it.isSuccessful) {
          _movies.value = it.body()
        }
      }
    }
  }

  fun getMovieDetails(movie_id: Int) {
    viewModelScope.launch {
      dataRepository.getMovieDetail(movie_id).collect {
        if (it.isSuccessful) {
          _movie.value = it.body()
        }
      }
    }
  }
}